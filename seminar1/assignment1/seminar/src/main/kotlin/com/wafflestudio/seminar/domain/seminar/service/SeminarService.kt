package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.GetSeminarInfoDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarParticipantDto
import com.wafflestudio.seminar.domain.seminar.exception.*
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.dto.UserDto
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
    private val instructorService: InstructorService,
    private val instructorRepository: InstructorRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
    private val participantRepository: ParticipantRepository,
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
        val seminarInfo = getSeminarResponseId(id)

        if (seminarInfo == null) { throw SeminarNotFoundException("Not Found Seminar.") }

        // 발견된 오류
        // capacity 체크가 안되고있음.
        // response 뽑아줄 두번째부터 갱신이 됨. 추가를 해줘야하는데 추가를 안함.
        if (requestRole == "participant") {
            // participantJoinSeminar
//            val seminarInfo = getSeminarResponseId(id)
            if (user.participantProfile == null) {
                throw NoAuthenticationForParticipate("No authentication for participate seminar.")
            }
            if (user.participantProfile!!.accepted == false) {
                throw ParticipantPermissionDenied("Accepted false.")
            }
            if (seminarInfo.seminarParticipant.count() > seminarInfo.capacity + 1) {
                throw CapacityOver("Capacity over.")
            }
            val seminarParticipant = SeminarParticipant(
                seminar = seminarInfo, participantProfile = user.participantProfile!!, joinedAt = LocalDateTime.now())

            // 간소화 영역 //
            user.participantProfile!!.seminarParticipant.add(seminarParticipant)
            userRepository.save(user)
            seminarInfo.addParticipant(seminarParticipant)
            // 간소화 영역 //

            // response 에 문제가 생긴듯하다.
            // controller 에서 userRepository 를 사용하는 것이 문제인가?

//            seminarRepository.save(seminarInfo)

//            seminarParticipantRepository.save(seminarParticipant)
//            user.participantProfile!!.enrollSeminar(seminarParticipant)
//            seminarInfo.addInstructor(user.instructorProfile!!)

        } else if (requestRole == "instructor") {
            // instructorJoinSeminar
            if (user.instructorProfile?.seminar != null) {
                throw AlreadyJoinSeminarAsInstructor()
            } else {
//                val seminarInfo = getSeminarResponseId(id)
                user.instructorProfile!!.seminar = seminarInfo
                seminarInfo.addInstructor(user.instructorProfile!!)

                seminarRepository.save(seminarInfo)
            }
        } else {
            throw RoleNotFoundException()
        }
        return seminarRepository.findSeminarById(id)!!
    }

    fun dropSeminar(id: Long, user: User): Seminar {
        val seminar = seminarRepository.findSeminarById(id)
        if (seminar == null) {
            throw SeminarNotFoundException("No data.")
        }
        if (seminar.instructors.indexOf(user.instructorProfile) != - 1) {
            throw InstructorNotAllowedException("You can't drop the seminar as instructor.")
        }
        val userParticipantProfile = participantService.getParticipantResponseById(user.id)
        seminar.seminarParticipant.find {it.participantProfile == userParticipantProfile}!!.isActive = false
        seminar.seminarParticipant.find {it.participantProfile == userParticipantProfile}!!.droppedAt = LocalDateTime.now()

        seminarRepository.save(seminar)
        return seminarRepository.findSeminarById(id)!!
    }
}