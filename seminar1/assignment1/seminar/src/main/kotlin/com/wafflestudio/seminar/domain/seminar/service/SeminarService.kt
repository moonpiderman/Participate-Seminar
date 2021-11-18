package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.GetSeminarInfoDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.*
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.exception.InstructorUnAuthorization
import com.wafflestudio.seminar.domain.user.exception.UserRoleException
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.InstructorService
import com.wafflestudio.seminar.domain.user.service.ParticipantService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.management.relation.RoleNotFoundException

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val userRepository: UserRepository,
    private val instructorRepository: InstructorRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
    private val participantRepository: ParticipantRepository,

    private val instructorService: InstructorService,
    private val participantService: ParticipantService,
) {
    fun checkUserRole(user: User): Boolean {
        if (user.roles.split(",").indexOf("instructor") == 0 ) {
            return true
        } else {
            throw NoAuthenticationCreatSeminar()
        }
    }

    fun saveSeminar(user: User, seminarRequest: SeminarDto.Request): Seminar {
        if (checkUserRole(user)) {
            val name = seminarRequest.name
            val capacity = seminarRequest.capacity
            val count = seminarRequest.count
            val time = seminarRequest.time
            val online = seminarRequest.online.lowercase().toBoolean()

            val newSeminar = seminarRepository.save(Seminar(name, capacity, count, time, online))
            user.instructorProfile!!.seminar = newSeminar

            newSeminar.addInstructor(user.instructorProfile!!)
            return seminarRepository.save(newSeminar)
        } else { throw UserRoleException() }
    }

    fun participateSeminar(id: Long, joinRequest: SeminarDto.JoinRequest, user: User): Seminar {
        val requestRole = joinRequest.role
        val seminarInfo = getSeminarResponseId(id)

        if (seminarInfo == null) { throw SeminarNotFoundException("Not Found Seminar.") }

        if (requestRole == "participant") {
            if (user.participantProfile == null) {
                throw NoAuthenticationForParticipate("No authentication for participate seminar.")
            }
            if (user.participantProfile!!.accepted == false) {
                throw ParticipantPermissionDenied("Accepted false.")
            }
            if (seminarInfo.seminarParticipant.count() > seminarInfo.capacity + 1) {
                throw CapacityOver("Capacity over.")
            }

            val seminarFilter = seminarInfo.seminarParticipant.find { it.participantProfile.user!!.email == user.email }
            if (seminarFilter != null) {
                if (!seminarFilter.isActive) { throw CannotJoinDroppingSeminar("Cannot join this seminar again.") }
            }

            val seminarParticipant = SeminarParticipant(
                seminar = seminarInfo, participantProfile = user.participantProfile!!, joinedAt = LocalDateTime.now())

            seminarInfo.addParticipant(seminarParticipant)
            user.participantProfile!!.enrollSeminar(seminarParticipant)
            userRepository.save(user)
            seminarRepository.save(seminarInfo)


        } else if (requestRole == "instructor") {
            if (user.instructorProfile?.seminar != null) {
                throw AlreadyJoinSeminarAsInstructor()
            } else {
                user.instructorProfile!!.seminar = seminarInfo
                seminarInfo.addInstructor(user.instructorProfile!!)
                userRepository.save(user)
                seminarRepository.save(seminarInfo)
            }
        } else {
            throw RoleNotFoundException()
        }
        return seminarRepository.findSeminarById(id)!!
    }

    fun modifySeminar(id: Long, modifyRequest: SeminarDto.Request?, user: User): Seminar {
        val seminarInfo = seminarRepository.findSeminarById(id)
        if (seminarInfo == null) {
            throw SeminarNotFoundException("This Seminar Not Found.")
        }
        if (user.roles.contains("instructor")) {
            if (user.instructorProfile!!.seminar?.id != id) {
                throw InstructorUnAuthorization("No Authentication.")
            }
            if (modifyRequest!!.capacity < seminarInfo.seminarParticipant.filter {it.isActive}.size.toLong()) {
                throw CapacityOver("Capacity is over.")
            }
            val changedOnline = modifyRequest.online.lowercase().toBoolean()

            seminarInfo.name = modifyRequest.name
            seminarInfo.capacity = modifyRequest.capacity
            seminarInfo.count = modifyRequest.count
            seminarInfo.time = modifyRequest.time
            seminarInfo.online = changedOnline

            seminarRepository.save(seminarInfo)
            return getSeminarResponseId(id)
        } else {
            throw NoAuthenticationModifySeminar("No Authentication to modify this seminar.")
        }
    }

    fun dropSeminar(id: Long, user: User): Seminar {
        val seminar = seminarRepository.findSeminarById(id)
        if (seminar == null) {
            throw SeminarNotFoundException("No data.")
        }
        if (seminar.instructors.map{it.user}.contains(user)) {
            throw InstructorNotAllowedException("Instructor cannot drop any seminar.")
        }
        if (!(user.participantProfile?.seminarParticipant!!.map {
            it.participantProfile.user }.contains(user))) {
            throw NoParticipateThisSeminar("No participate this seminar.")
        }

        seminar.seminarParticipant.find {it.seminar == seminar}!!.isActive = false
        seminar.seminarParticipant.find {it.seminar == seminar}!!.droppedAt = LocalDateTime.now()

        seminarRepository.save(seminar)
        return seminarRepository.findSeminarById(id)!!
    }



    fun getSeminarResponseId(id: Long): Seminar {
        return seminarRepository.findByIdOrNull(id) ?: throw SeminarNotFoundException()
    }

    fun getSeminarInfoByName(seminarName: String, seminarOrder: Boolean): List<GetSeminarInfoDto.Response> {
        val seminars = seminarRepository.findAll()
        var listOfSeminar = seminars.sortedWith(compareBy({it.createdAt}))
        if (seminarOrder) listOfSeminar = listOfSeminar.reversed()
        return listOfSeminar.map { it -> GetSeminarInfoDto.Response(it) }
    }

    fun getAllSeminarInfo(seminarOrder: Boolean): List<GetSeminarInfoDto.Response> {
        val seminars = seminarRepository.findAll()
        var listOfSeminar = seminars.sortedWith(compareBy({it.createdAt}))
        if (seminarOrder) listOfSeminar = listOfSeminar.reversed()
        return listOfSeminar.map { it -> GetSeminarInfoDto.Response(it) }
    }
}