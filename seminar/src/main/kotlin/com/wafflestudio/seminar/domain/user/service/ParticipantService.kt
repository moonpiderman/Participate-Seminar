package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.exception.AlreadyParticipantUser
import com.wafflestudio.seminar.domain.user.exception.UserRoleException
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val userRepository: UserRepository,
) {
    fun enrollParticipant(
        currentUser: User, participantRequest: ParticipantDto.ParticipantRequest
    ): User {
        if (currentUser.roles == "instructor") {
            val setRole = currentUser.roles.split(",").toSet()
            if (setRole == setOf<String>("instructor", "participant")) {
                throw AlreadyParticipantUser("Already a participant user.")
            }
        } else {
            throw UserRoleException("Check your role.")
        }
        currentUser.roles = "instructor,participant"

        var university = participantRequest.university
        if (university == null) university = ""

        var accepted = participantRequest.accepted
        if (accepted == null) accepted = "true"

        val changeAccepted = accepted.lowercase().toBoolean()

//        val newParticipantInfo = participantRepository.save(ParticipantProfile(university, changeAccepted, currentUser))
        val newParticipantInfo = ParticipantProfile(university, changeAccepted)
        currentUser.participantProfile = newParticipantInfo

        val addParticipant = userRepository.save(currentUser)
        return addParticipant
    }
}