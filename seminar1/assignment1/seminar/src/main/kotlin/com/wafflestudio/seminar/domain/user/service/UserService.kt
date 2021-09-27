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
    private val instructorRepository: InstructorRepository,

    private val participantService: ParticipantService,
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        if (userRepository.existsByEmail(signupRequest.email)) throw UserAlreadyExistsException()
        if ((signupRequest.role != "participant") && (signupRequest.role != "instructor")) throw UserRoleException()
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.email, signupRequest.name, encodedPassword, signupRequest.role))
    }

    // user 의 role 을 판단해서 넘겨주는 service 로직을 형성
    fun enrollRole(userId: User, signupRequest: UserDto.SignupRequest) {
        // role = participant
        if (signupRequest.role == "participant") {
            // 여기서 객체의 field 값을 넘겨주어야하나?
            participantRepository.save(
                ParticipantProfile(
                    signupRequest.university,
                    signupRequest.accepted,
                    user = getUserResponseId(userId.id)
                )
            )
        }
        // role = instructor
        if (signupRequest.role == "instructor") {
            instructorRepository.save(
                InstructorProfile(
                    signupRequest.company,
                    signupRequest.year,
                    user = getUserResponseId(userId.id)
                )
            )
        }
    }

    // modify function
    fun modifyMe(user: User, modifyRequest: UserDto.ModifyRequest): User {
        // 현재 user의 정보를 save 해주어야한다.
        // 그러지말고 null 이라면, 혹은 "" 이라면 변동없게끔 해줘야한다. 우선 이게 되는지 체크하자.
        // userId 를 통해 participant 정보를 가져올 participantService 를 생성해볼까?

        var updateUserInfo = participantService.modifyParticipantInfo(modifyRequest)
        user.participantProfile = updateUserInfo
        return user
    }

    fun getUserResponseId(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}