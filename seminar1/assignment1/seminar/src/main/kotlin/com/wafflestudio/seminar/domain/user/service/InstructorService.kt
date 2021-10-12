package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class InstructorService(
    private val instructorRepository: InstructorRepository,
    private val userRepository: UserRepository,
) {
    fun getInstructorResponseId(id: Long?): InstructorProfile {
        return instructorRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}