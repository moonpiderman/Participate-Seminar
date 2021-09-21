package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar")
class Seminar (
    @field:NotBlank
    val name: String,

    @field:NotBlank
    @Min(1)
    val capacity: Long,

    @field:NotBlank
    @Min(1)
    val count: Long,

    @field:NotBlank
    val time: LocalDateTime,

    @field:NotNull
    val online: Boolean = true,

    @CreatedDate
    @Column(name = "joined_at")
    val joinedAt: LocalDateTime,

    @Column(name = "is_active")
    val isActive: Boolean,

    @UpdateTimestamp
    @Column(name = "dropped_at")
    val droppedAt: LocalDateTime? = null

) : BaseEntity()