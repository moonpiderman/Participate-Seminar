package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import javax.validation.constraints.NotNull

class UserDto {
    data class Response(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null
    ) {
        constructor(user: User) : this(
            user.id,
            user.username,
            user.email
        )
    }

    data class CreateRequest(
        @field:NotNull
        var username: String?,

        @field:NotNull
        var email: String?
    )
}