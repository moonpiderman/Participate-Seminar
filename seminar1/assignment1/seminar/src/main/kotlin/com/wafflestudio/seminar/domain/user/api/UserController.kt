package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.UserEmailDuplicate
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
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

//    @PostMapping("/")
//    fun createUser(@ModelAttribute @RequestBody @Valid body: UserDto.CreateRequest): UserDto.Response {
////        user.username = body.username
////        user.email = body.email
//        val newUser = User(username = body.username, email = body.email)
//        userRepository.save(newUser)
//        return UserDto.Response(id = newUser.id, username = newUser.username, email = newUser.email)
//    }
    @PostMapping("/")
    fun createUser(@ModelAttribute @Valid body: UserDto.CreateRequest, bindingResult: BindingResult) : ResponseEntity<User>{
        if (bindingResult.hasErrors()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try {
            val newUser = User(
                username = body.username,
                email = body.email
            )
            userRepository.save(newUser)
            ResponseEntity(newUser, HttpStatus.CREATED)
        } catch (e: UserEmailDuplicate) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/me/")
//    fun userMe(@RequestHeader("User-Id") userId: Long): UserDto.Response {
//        // 입력받은 id 와 같은 id 가 DB에 있는 데이터와 같은 것의 response를 불러오기.
//        val userInfo = userService.getUserInfoByUserId(userId)
//        return UserDto.Response(id = userId, username = userInfo!!.username, email = userInfo.email)
//    }
    fun userMe(@RequestHeader("User-Id") userId: Long): ResponseEntity<UserDto.Response> {
        return try{
            val userInfo = userService.getUserInfoByUserId(userId)
            val responseBody = modelMapper.map(userInfo, UserDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: UserNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}