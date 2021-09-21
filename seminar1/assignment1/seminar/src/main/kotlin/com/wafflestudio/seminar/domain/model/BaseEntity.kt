package com.wafflestudio.seminar.domain.model

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@MappedSuperclass
//@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0
}
