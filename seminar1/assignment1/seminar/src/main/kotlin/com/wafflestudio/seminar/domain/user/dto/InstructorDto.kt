package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile

class InstructorDto {
    data class Response(
        val id: Long,
        val company: String?,
        val year: Long? = null,
        val charge: SeminarDto.Response?
        // charges
    ) {
        constructor(instructor: InstructorProfile) : this(
            id = instructor.id,
            company = instructor.company,
            year = instructor.year,
            charge = instructor.seminar?.let { SeminarDto.Response(it) }
        )
    }
}