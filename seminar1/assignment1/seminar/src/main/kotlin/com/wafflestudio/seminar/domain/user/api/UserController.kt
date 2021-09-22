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
        val enroll = userService.enrollRole(user, signupRequest)
        return ResponseEntity.noContent().header("Authentication", jwtTokenProvider.generateToken(user.email)).build()
    }

    @GetMapping("/me/")
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @GetMapping("/{id}/")
    fun getIdentityUser(@PathVariable("id") id: Long): UserDto.Response {
        val userReponse = userService.getUserResponseId(id)
        return UserDto.Response(userReponse)
    }

    // participant 인지 instructor 인지 알 수 없으니 request의 값들이 must일 필요는 없다.
    // 값을 제한해주는 것을 막자.
//    @PutMapping("/me/")
//    fun modifyMe(@Valid @RequestBody)

    @PostMapping("/participant/")
    fun userParticipant(@Valid @RequestBody participantRequest: ParticipantDto.ParticipantRequest): ResponseEntity<ParticipantDto.Response> {
        val partInfo = participantService.enrollParticipant(participantRequest)
        val partInfoResponse = ParticipantDto.Response(partInfo)
//        return ResponseEntity.noContent().build()
        return ResponseEntity(partInfoResponse, HttpStatus.CREATED)
    }
}
