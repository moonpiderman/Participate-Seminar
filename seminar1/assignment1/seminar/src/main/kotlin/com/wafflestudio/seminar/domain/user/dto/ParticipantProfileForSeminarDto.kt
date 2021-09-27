package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime

class ParticipantProfileForSeminarDto {
    data class Response(
        val id: Long,
        val name: String,
        val email: String,
//        val university: String,
//        val joinedAt: LocalDateTime,
//        val isActive: Boolean,
//        val droppedAt: LocalDateTime,
    ) {
        constructor(user: User): this(
            id = user.id,
            name = user.name,
            email = user.email,
//            university = user.participantProfiles?.university,
//            joinedAt = user.participantProfiles?.seminarParticipant?.let {joinedAt}
//            isActive = user.participantProfiles?.seminarParticipant?.map {it -> it.isActive},
        )
    }
}