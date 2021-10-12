package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.*
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
    fun enrollRole(userInfo: User, signupRequest: UserDto.SignupRequest) {
        // role = participant
        if (signupRequest.role == "participant") {
            userInfo.roles = signupRequest.role
            participantRepository.save(
                ParticipantProfile(
                    signupRequest.university,
                    signupRequest.accepted,
                    user = getUserResponseId(userInfo.id)
                )
            )
        }
        // role = instructor
        if (signupRequest.role == "instructor") {
            userInfo.roles = signupRequest.role
            instructorRepository.save(
                InstructorProfile(
                    signupRequest.company,
                    signupRequest.year,
                    user = getUserResponseId(userInfo.id)
                )
            )
        }
        userRepository.save(userInfo)
    }

    fun modifyMe(currentUser: User, modifyRequest: UserDto.ModifyRequest): User {
        if (currentUser.email != modifyRequest.email) {
            throw UserNotFoundException("You can't change your email.")
        }
        val passwordEncoding = passwordEncoder.encode(modifyRequest.password)
        currentUser.password = passwordEncoding
        val userRole = currentUser.roles
        val newYear = modifyRequest.year
        var newCompany = modifyRequest.company
        var newUniversity = modifyRequest.university
        var newAccepted = modifyRequest.accepted

        if (userRole == "participant") {
            if (newUniversity == null) newUniversity = ""
            if (newAccepted == null) newAccepted = "true"
            val changeAccepted = newAccepted.lowercase().toBoolean()
            currentUser.participantProfile?.university = newUniversity
            currentUser.participantProfile?.accepted = changeAccepted
        } else if (userRole == "instructor") {
            if (newCompany == null) newCompany = ""
            if (newYear != null && newYear <= 0) throw InstructorYearException()
            currentUser.instructorProfile?.company = newCompany
            currentUser.instructorProfile?.year = newYear
        } else if (userRole == "instructor,participant") {
            if (newUniversity == null) newUniversity = ""
            if (newAccepted == null) newAccepted = "true"
            val changeAccepted = newAccepted.lowercase().toBoolean()
            if (newCompany == null) newCompany = ""
            if (newYear != null && newYear <= 0) throw InstructorYearException()

            currentUser.instructorProfile?.company = newCompany
            currentUser.instructorProfile?.year = newYear
            currentUser.participantProfile?.university = newUniversity
            currentUser.participantProfile?.accepted = changeAccepted

        } else { throw RoleException() }
        return userRepository.save(currentUser)
    }

    fun getUserResponseId(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}