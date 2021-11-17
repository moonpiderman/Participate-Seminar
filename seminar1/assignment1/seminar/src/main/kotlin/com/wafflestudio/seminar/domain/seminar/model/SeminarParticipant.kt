package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "seminar_participant")
class SeminarParticipant(
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val seminar: Seminar,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val participantProfile: ParticipantProfile,

    @Column(name = "joined_at")
    var joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @Column(nullable = true)
    var droppedAt: LocalDateTime? = null,
) : BaseEntity()
