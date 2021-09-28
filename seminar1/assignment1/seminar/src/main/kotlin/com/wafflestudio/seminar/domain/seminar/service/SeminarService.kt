package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.GetSeminarInfoDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.NoAuthenticationCreatSeminar
import com.wafflestudio.seminar.domain.seminar.exception.SeminarNotFoundException
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.InstructorService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val userRepository: UserRepository,
    private val instructorService: InstructorService,
    private val instructorRepository: InstructorRepository,
) {
    fun checkUserRole(user: User): Boolean {
//        if (user.roles != "instructor") {
//            return false
//        } else { return true }
        if (user.roles.split(",").indexOf("instructor") == 0 ) {
            return true
        } else if (user.roles == "instructor"){
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
}