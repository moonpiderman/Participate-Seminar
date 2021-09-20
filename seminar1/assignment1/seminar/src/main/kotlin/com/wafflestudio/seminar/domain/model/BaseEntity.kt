package com.wafflestudio.seminar.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
//@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    @CreatedDate
    open val created_at: LocalDateTime? = null

    @LastModifiedDate
    open val updated_at: LocalDateTime? = null
}
