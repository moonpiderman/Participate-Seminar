package com.wafflestudio.seminar.domain.user.model

import org.springframework.validation.annotation.Validated
import java.awt.font.TransformAttribute.IDENTITY
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_name")
    @NotNull
    var username: String? = null,

    // email 부분을 중복체크 해야한다.
    // api 단에서 해야하는지 model 단에서 어노테이션으로 가능한지 알아보기
    @Column(name = "email")
    @Email
    @NotNull
    var email: String? = null
)