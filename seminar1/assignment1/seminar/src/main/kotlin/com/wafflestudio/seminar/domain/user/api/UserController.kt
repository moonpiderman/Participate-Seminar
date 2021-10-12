package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.service.ParticipantService
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.HttpStatus
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
        userService.enrollRole(user, signupRequest)
        return ResponseEntity.noContent().header("Authentication", jwtTokenProvider.generateToken(user.email)).build()
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): ResponseEntity<UserDto.Response> {
        return ResponseEntity(UserDto.Response(user), HttpStatus.OK)
    }

    @GetMapping("/{id}/")
    fun getIdentityUser(@PathVariable("id") id: Long): ResponseEntity<UserDto.Response> {
        val userResponse = userService.getUserResponseId(id)
        return ResponseEntity(UserDto.Response(userResponse), HttpStatus.OK)
    }

    @PutMapping("/me/")
    fun modifyUser(
        @CurrentUser user: User,
        @Valid @RequestBody modifyRequest: UserDto.ModifyRequest
    ): ResponseEntity<UserDto.Response> {
        val editedUser = userService.modifyMe(user, modifyRequest)
        return ResponseEntity(UserDto.Response(editedUser), HttpStatus.OK)
    }

    @PostMapping("/participant/")
    fun userParticipant(
        @CurrentUser user: User,
        @Valid @RequestBody participantRequest: ParticipantDto.ParticipantRequest
    ): ResponseEntity<UserDto.Response> {
        val partInfo = participantService.enrollParticipant(user, participantRequest)
        return ResponseEntity(UserDto.Response(partInfo), HttpStatus.CREATED)
    }
}
