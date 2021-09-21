package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import javax.persistence.*

@Entity
@Table(name = "seminar_participant")
class SeminarParticipant (
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val seminar: Seminar,

    // ParticipantProfile 에서 link 해줄 부분이 필요하다.
    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "seminarParticipant")
    val participantProfile: List<ParticipantProfile> = listOf()

) : BaseEntity()