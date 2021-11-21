package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "participant")
class ParticipantProfile (
    @Column
    var university: String? = "",

    @field:NotNull
    var accepted: Boolean? = true,

    @OneToOne(mappedBy = "participantProfile")
    val user: User? = null,

    @OneToMany(mappedBy = "participantProfile", fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    val seminarParticipant : MutableList<SeminarParticipant> = mutableListOf()
) : BaseTimeEntity() {
    fun enrollSeminar(mappingSeminar: SeminarParticipant) {
        seminarParticipant.add(mappingSeminar)
    }
}