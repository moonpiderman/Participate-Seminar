package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar")
class Seminar (
    @field:NotBlank
    val name: String,

    @field:NotNull
    @Min(1)
    val capacity: Long,

    @field:NotNull
    @Min(1)
    val count: Long,

    @field:NotNull
    val time: LocalTime,

    @field:NotNull
    val online: Boolean = true,

    @CreatedDate
    @Column(name = "joined_at")
    val joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @UpdateTimestamp
    @Column(name = "dropped_at")
    val droppedAt: LocalDateTime? = null

) : BaseEntity()