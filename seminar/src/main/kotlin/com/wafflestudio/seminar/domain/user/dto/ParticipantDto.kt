package com.wafflestudio.seminar.domain.user.dto

import com.sun.istack.NotNull
import com.wafflestudio.seminar.domain.seminar.dto.SeminarParticipantDto
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile

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
                    SeminarParticipantDto.ResponseForSeminarOfParticipant(it)
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
