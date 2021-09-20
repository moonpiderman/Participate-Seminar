package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "seminar_instructor")
class InstructorProfile (
    @CreatedDate
    val created_at: LocalDateTime,

    @LastModifiedDate
    val updated_at: LocalDateTime

    ) : BaseEntity()