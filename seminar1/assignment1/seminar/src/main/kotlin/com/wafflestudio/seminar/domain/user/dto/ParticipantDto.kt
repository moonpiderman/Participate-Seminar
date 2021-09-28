package com.wafflestudio.seminar.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.sun.istack.NotNull
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarParticipantDto
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime

class ParticipantDto {
    data class Response(
        val id: Long,
        val university: String?,
        val accepted: Boolean? = true,
        // seminars
        val seminars: List<SeminarParticipantDto.ResponseForSeminarOfParticipant>,
    ) {
        constructor(participant: ParticipantProfile) : this(
            id = participant.id,
            university = participant.university,
            accepted = participant.accepted,
            seminars = participant.seminarParticipant. map {
                it -> SeminarParticipantDto.ResponseForSeminarOfParticipant(it)
            }
        )
    }

    data class ParticipantRequest(
        @field:NotNull
        val university: String? = "",
        @field:NotNull
        val accepted: String? = "",
    )
}