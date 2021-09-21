package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import org.apache.tomcat.jni.Local
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Null

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val name: String,
        val date_joined: LocalDateTime
        // participant_profile
//        val participant_profile: ParticipantDto.Response,
        // instructor_profile
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name,
            date_joined = user.date_joined
//            participant_profile =
        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String,
        @field:NotBlank
        val role: String,

        @field:NotNull
        val university: String? = "",
        @field:NotNull
        val accepted: Boolean? = true,

        @field:NotNull
        val company: String? = "",
        @field:NotNull
        val year: Long? = 0
    )
}