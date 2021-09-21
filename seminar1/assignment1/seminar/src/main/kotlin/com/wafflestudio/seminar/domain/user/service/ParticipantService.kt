package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository
) {
    fun enrollParticipant(participantRequest: ParticipantDto.ParticipantRequest): ParticipantProfile {
        return participantRepository.save(ParticipantProfile(participantRequest.university, participantRequest.accepted))
    }
}