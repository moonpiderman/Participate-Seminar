package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.UserAlreadyExistsException
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.exception.UserRoleException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,

    private val participantRepository: ParticipantRepository,
    private val instructorRepository: InstructorRepository
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        if (userRepository.existsByEmail(signupRequest.email)) throw UserAlreadyExistsException()
        if ((signupRequest.role != "participant") && (signupRequest.role != "instructor")) throw UserRoleException()
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.email, signupRequest.name, encodedPassword, signupRequest.role))
    }

    // user 의 role 을 판단해서 넘겨주는 service 로직을 형성
    fun enrollRole(userId: User, signupRequest: UserDto.SignupRequest) {
        // role 에 따라 각 service 에 객체를 던져주어야하는가?
        // role = participant
        if (signupRequest.role == "participant") {
            // 여기서 객체의 field 값을 넘겨주어야하나?
            participantRepository.save(ParticipantProfile(signupRequest.university, signupRequest.accepted, user = getUserResponseId(userId.id)))
        }
        // role = instructor
        if (signupRequest.role == "instructor") {
            instructorRepository.save(InstructorProfile(signupRequest.company, signupRequest.year, user = getUserResponseId(userId.id)))
        }
    }

    fun getUserResponseId(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}