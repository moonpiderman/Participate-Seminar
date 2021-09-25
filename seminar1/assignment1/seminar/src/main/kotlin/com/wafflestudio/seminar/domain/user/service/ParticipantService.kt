package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.exception.UserRoleException
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository
) {
    // instructor 가 다른 seminar 의 participant 로 참가하는 경우
    fun enrollParticipant(
        currentUser: User,
        participantRequest: ParticipantDto.ParticipantRequest
    ): ParticipantProfile {
        if (currentUser.roles == "instructor") {
            return participantRepository.save(
                ParticipantProfile(
                    participantRequest.university,
                    participantRequest.accepted,
                    user = currentUser
                )
            )
        } else {
            throw UserRoleException()
        }
    }
}