package com.wafflestudio.seminar.domain.user.repository

import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import org.springframework.data.jpa.repository.JpaRepository

interface InstructorRepository: JpaRepository<InstructorProfile, Long?> {
    fun findByUserId(userId: Long): InstructorProfile
}