package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import net.bytebuddy.implementation.bind.annotation.Default
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "participant")
class ParticipantProfile (
    @Column
    val university: String = "",

    @field:NotBlank
    val accepted: Boolean = true,

    // seminars

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(referencedColumnName = "id")
    val seminarParticipant: SeminarParticipant? = null
) : BaseEntity()