package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.user.dto.InstructorsProfileForSeminarDto

class GetSeminarInfoDto {
    data class Response(
        val id: Long,
        val name: String,
        val instructors: List<InstructorsProfileForSeminarDto.Response>,
        val participantCount: Int,
    ) {
        constructor(seminar: Seminar): this(
            id = seminar.id,
            name = seminar.name,
            instructors = seminar.instructors.map {
                it -> InstructorsProfileForSeminarDto.Response(it.user!!)
            },
            participantCount = seminar.seminarParticipant.filter { it.isActive }.size
        )
    }
}