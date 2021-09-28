package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.GetSeminarInfoDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.*
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.InstructorService
import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.NotAllowedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.management.relation.RoleNotFoundException

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val userRepository: UserRepository,
    private val instructorService: InstructorService,
    private val instructorRepository: InstructorRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
    private val participantRepository: ParticipantRepository,
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
            val storedSeminar = seminarRepository.save(
                Seminar(seminarRequest.name, seminarRequest.capacity, seminarRequest.count, seminarRequest.time))
            val instructorId = user.instructorProfile?.id
            val instructor = instructorService.getInstructorResponseId(instructorId)
            instructor.seminar = storedSeminar
            user.instructorProfile = instructor
            userRepository.save(user)
            return storedSeminar
        } else { throw NoAuthenticationCreatSeminar() }
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

    // instructor,participant 인 경우 -> ?
    // participant 인 경우
    // instructor 인 경우
    fun joinSeminar(id: Long, joinRequest: SeminarDto.JoinRequest, user: User): Seminar {
        val requestRole = joinRequest.role
        if (requestRole == "participant") {
            // participantJoinSeminar
            val seminarInfo = getSeminarResponseId(id)
            if (user.participantProfile == null) {
                throw NoAuthenticationForParticipate("No authentication for participate seminar.")
            }
            if (user.participantProfile?.accepted == false) {
                throw ParticipantPermissionDenied("Accepted false.")
            }
            if (seminarInfo.seminarParticipant.count() > seminarInfo.capacity + 1) {
                throw CapacityOver("Capacity over.")
            }
            val seminarParticipant = SeminarParticipant(
                seminar = seminarInfo, participantProfile = user.participantProfile!!, joinedAt = LocalDateTime.now())
            user.participantProfile!!.enrollSeminar(seminarParticipant)
            userRepository.save(user)
            seminarRepository.save(seminarInfo)
            seminarInfo.addParticipant(seminarParticipant)

        } else if (requestRole == "instructor") {
            // instructorJoinSeminar
            if (user.instructorProfile?.seminar != null) {
                throw AlreadyJoinSeminarAsInstructor()
            } else {
                val seminarInfo = getSeminarResponseId(id)
                user.instructorProfile!!.seminar = seminarInfo
                userRepository.save(user)
                seminarRepository.save(seminarInfo)
                seminarInfo.addInstructor(user.instructorProfile!!)
            }
        } else {
            throw RoleNotFoundException()
        }
        return seminarRepository.findSeminarById(id)!!
    }

    fun dropSeminar(id: Long, user: User): Seminar {
        val seminar = seminarRepository.findSeminarById(id)
        val seminarParticipant =
            seminarParticipantRepository.findSeminarParticipantByParticipantProfileId(user.participantProfile!!.id)
//        val participant =
//            participantRepository.findBySeminarParticipant(seminarParticipant)
        if (seminar == null) {
            throw SeminarNotFoundException("No data.")
        }
        if (seminar.instructors.indexOf(user.instructorProfile) != - 1) {
            throw InstructorNotAllowedException("You can't drop the seminar as instructor.")
        }
        seminarParticipant!!.isActive = false
        seminarParticipant!!.droppedAt = LocalDateTime.now()
        seminarParticipantRepository.save(seminarParticipant)
        seminarRepository.save(seminar)
        return seminarRepository.findSeminarById(id)!!
    }
}