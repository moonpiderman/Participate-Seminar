package com.wafflestudio.seminar.domain.user.model


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
    // api 단에서 중복체크
    @Column(name = "email", unique = true)
    @Email
    @NotNull
    var email: String? = null
)