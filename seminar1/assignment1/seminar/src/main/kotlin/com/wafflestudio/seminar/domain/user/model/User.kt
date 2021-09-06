package com.wafflestudio.seminar.domain.user.model

import java.awt.font.TransformAttribute.IDENTITY
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_name")
    @NotNull
    var username: String? = null,

    @Column(name = "email")
    @NotNull
    var email: String? = null
)