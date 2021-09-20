package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "seminar_participant")
class ParticipantProfile (
    @CreatedDate
    val created_at: LocalDateTime,

    @LastModifiedDate
    val updated_at: LocalDateTime

) : BaseEntity()