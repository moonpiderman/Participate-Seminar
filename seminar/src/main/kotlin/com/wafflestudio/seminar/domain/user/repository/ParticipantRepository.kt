package com.wafflestudio.seminar.domain.user.repository

import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository: JpaRepository<ParticipantProfile, Long?> {
    fun findAllByUserId(userId: Long): ParticipantProfile
    fun findBySeminarParticipant(seminarParticipant: SeminarParticipant) : ParticipantProfile
}