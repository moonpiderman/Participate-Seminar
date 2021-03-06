package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime

class SeminarParticipantDto {
    data class ResponseForSeminarOfParticipant(
        val id: Long,
        val name: String,
        val joinedAt: LocalDateTime,
        val isActive: Boolean,
        val droppedAt: LocalDateTime?,
    ) {
        constructor(seminarParticipant: SeminarParticipant): this(
            id = seminarParticipant.seminar.id,
            name = seminarParticipant.seminar.name,
            joinedAt = seminarParticipant.joinedAt!!,
            isActive = seminarParticipant.isActive,
            droppedAt = seminarParticipant.droppedAt
        )
    }

    data class ResponseForSeminarParticipants(
        val id: Long,
        val name: String,
        val email: String,
        val university: String?,
        val joinedAt: LocalDateTime,
        val isActive: Boolean,
        val droppedAt: LocalDateTime?,
    ) {
        constructor(user: User, seminarParticipant: SeminarParticipant): this(
            id = user.id,
            name = user.name,
            email = user.email,
            university = user.participantProfile?.university,
            joinedAt = seminarParticipant.joinedAt!!,
            isActive = seminarParticipant.isActive,
            droppedAt = seminarParticipant.droppedAt,
        )
    }
}