package com.wafflestudio.seminar.domain.user.dto

import javax.validation.constraints.NotNull

class UserDto {
    data class Response(
        var id: Long? = 0,
        var username: String? = "",
        var email: String? = ""
    )

    data class CreateRequest(
        @field:NotNull
        var username: String? = "",

        @field:NotNull
        var email: String? = ""
    )
}