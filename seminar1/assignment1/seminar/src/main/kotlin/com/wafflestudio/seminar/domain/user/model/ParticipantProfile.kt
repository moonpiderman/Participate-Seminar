package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "seminar_participant")
class ParticipantProfile (

) : BaseEntity()