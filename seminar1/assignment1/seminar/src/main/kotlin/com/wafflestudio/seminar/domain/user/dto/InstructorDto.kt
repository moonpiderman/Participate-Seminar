package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.dto.ChargeDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile

class InstructorDto {
    data class Response(
        val id: Long,
        val company: String?,
        val year: Int? = null,
        val charge: ChargeDto.ChargeResponse?
    ) {
        constructor(instructor: InstructorProfile) : this(
            id = instructor.id,
            company = instructor.company,
            year = instructor.year,
            charge = instructor.seminar?.let { ChargeDto.ChargeResponse(it) }
        )
    }
}