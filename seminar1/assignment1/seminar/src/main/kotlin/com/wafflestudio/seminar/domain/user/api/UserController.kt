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
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @GetMapping("/{id}/")
    fun getIdentityUser(@PathVariable("id") id: Long): UserDto.Response {
        val userResponse = userService.getUserResponseId(id)
        return UserDto.Response(userResponse)
    }

    // participant 인지 instructor 인지 알 수 없으니 request의 값들이 must일 필요는 없다.
    // 값을 제한해주는 것을 막자.

    // 참여자인 User 는 university 를 수정할 수 있다.
    // 진행자인 User 는 company 와 year 를 수정할 수 있다.
    // 참여자 혹은 진행자인 User 는 Participant 의 accepted 를 제외하고 모든 정보를 수정할 수 있다.


    @PutMapping("/me/")
    fun modifyUser(@CurrentUser user: User, @Valid @RequestBody modifyRequest: UserDto.ModifyRequest): UserDto.Response {
        var editUserInfo = userService.modifyMe(user, modifyRequest)
        return UserDto.Response(editUserInfo)
    }

    @PostMapping("/participant/")
    fun userParticipant(@CurrentUser user: User, @Valid @RequestBody participantRequest: ParticipantDto.ParticipantRequest): ResponseEntity<ParticipantDto.Response> {
        val partInfo = participantService.enrollParticipant(user, participantRequest)
        val partInfoResponse = ParticipantDto.Response(partInfo)
        return ResponseEntity(partInfoResponse, HttpStatus.CREATED)
    }
}
