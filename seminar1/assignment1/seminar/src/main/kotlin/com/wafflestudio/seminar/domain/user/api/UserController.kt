package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
//    private val user: User,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper
) {
    @PostMapping("/")
    fun createUser(@ModelAttribute @RequestBody @Valid body: UserDto.CreateRequest): UserDto.Response {
//        user.username = body.username
//        user.email = body.email
        val new_user = User(username = body.username, email = body.email)
        userRepository.save(new_user)
        return UserDto.Response(id = new_user.id, username = new_user.username, email = new_user.email)
    }
}