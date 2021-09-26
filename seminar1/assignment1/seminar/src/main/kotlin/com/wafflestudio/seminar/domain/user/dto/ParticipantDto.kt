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

    data class ResponseWithSeminars(
        val id: Long,
        val university: String?,
        val accepted: Boolean? = true,
//        val seminars: SeminarParticipantDto.ResponseForSeminarOfParticipant?

//        @JsonProperty(namespace = "created_at")
//        var created_at: LocalDateTime,
//        @JsonProperty(namespace = "updated_at")
//        val updated_at: LocalDateTime?,
    ) {
        constructor(participant: ParticipantProfile) : this(
            id = participant.id,
            university = participant.university,
            accepted = participant.accepted,
//            created_at = participant.createdAt,
//            updated_at = participant.updatedAt,
            // seminars 를 어떻게 response 로 뽑아주지?

            // seminars' constructor
//            seminars = participant.seminarParticipant?.let {
//                SeminarParticipantDto.ResponseForSeminarOfParticipant(it) }
        )
    }

    data class ParticipantRequest(
        @field:NotNull
        val university: String = "",
        @field:NotNull
        val accepted: Boolean = true
    )
}