package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.exception.NoAuthenticationCreatSeminar
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository
) {
    fun checkUserRole(user: User, seminarRequest: SeminarDto.Request): Seminar {
        if (user.roles == "instructor") {
            return seminarRepository.save(Seminar(seminarRequest.name, seminarRequest.capacity, seminarRequest.count, seminarRequest.time))
        } else { throw NoAuthenticationCreatSeminar() }
    }
}