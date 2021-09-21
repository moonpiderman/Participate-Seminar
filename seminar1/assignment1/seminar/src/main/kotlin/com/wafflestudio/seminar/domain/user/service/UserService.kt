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
        // if 만들어서 role 이 participant, instructor 인지에 따라서 각각 테이블에 데이터 생성해줘야함.
        // participant 라면 participantRepository 에 접근해서 save 해줘야하지 않을까?

        // role = participant
        if (signupRequest.role == "participant") {
            // 여기서 객체의 field 값을 넘겨주어야하나?
            participantRepository.save(ParticipantProfile(signupRequest.university, signupRequest.accepted))
        }

        // role = instructor
        if (signupRequest.role == "instructor") {
            instructorRepository.save(InstructorProfile(signupRequest.company, signupRequest.year))
        }

        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.email, signupRequest.name, encodedPassword, signupRequest.role))
    }

    fun getUserResponseId(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}