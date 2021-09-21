package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.service.ParticipantService
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val participantService: ParticipantService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/")
    fun signup(@Valid @RequestBody signupRequest: UserDto.SignupRequest): ResponseEntity<UserDto.Response> {
        val user = userService.signup(signupRequest)
        return ResponseEntity.noContent().header("Authentication", jwtTokenProvider.generateToken(user.email)).build()
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @PostMapping("/participant/")
    fun userParticipant(@Valid @RequestBody participantRequest: ParticipantDto.ParticipantRequest): ResponseEntity<ParticipantDto.Response> {
        // role 이 participant도 아니고, instructor 도 아니라면 error 발생. (400)
        participantService.enrollParticipant(participantRequest)
        return ResponseEntity.noContent().build()
    }
}
