package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.model.User
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val modelMapper: ModelMapper
) {
    @PostMapping("/")
    fun createUser(
        @ModelAttribute @Valid body: UserDto.CreateRequest): UserDto.Response {
        return UserDto.Response()
    }
}