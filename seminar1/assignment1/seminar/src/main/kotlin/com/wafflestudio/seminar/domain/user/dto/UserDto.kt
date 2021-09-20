package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import org.apache.tomcat.jni.Local
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val name: String,
        val date_joined: LocalDateTime,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name,
            date_joined = user.date_joined
        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String
    )
}