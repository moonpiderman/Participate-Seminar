package com.wafflestudio.seminar.domain.user.dto

import com.sun.istack.NotNull
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime

class ParticipantDto {
    data class Response(
        val id: Long,
        val university: String?,
        val accepted: Boolean? = true,
        // seminars
        var created_at: LocalDateTime,
        val updated_at: LocalDateTime?
    ) {
        constructor(participant: ParticipantProfile) : this(
            id = participant.id,
            university = participant.university,
            accepted = participant.accepted,
            created_at = participant.createdAt,
            updated_at = participant.updatedAt
        )
    }

    data class ParticipantRequest(
        @field:NotNull
        val university: String = "",
        @field:NotNull
        val accepted: Boolean = true
    )
}