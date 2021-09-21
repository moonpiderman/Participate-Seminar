package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService(
    private val instructorRepository: InstructorRepository
) {
}