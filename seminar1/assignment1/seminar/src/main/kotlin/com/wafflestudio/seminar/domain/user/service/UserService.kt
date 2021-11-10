package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.*
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signup(signupRequest: UserDto.SignupRequest): User {
        var participant: ParticipantProfile? = null
        var instructor: InstructorProfile? = null
        if (userRepository.existsByEmail(signupRequest.email)) throw UserAlreadyExistsException()
        val email = signupRequest.email
        val name = signupRequest.name
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        val role = signupRequest.role
        val university = signupRequest.university
        val accepted = signupRequest.accepted
        val company = signupRequest.company
        val year = signupRequest.year

        when (role) {
            "instructor" -> instructor = InstructorProfile(company, year)
            "participant" -> participant = ParticipantProfile(university, accepted)
            else -> throw UserRoleException()
        }

        return userRepository.save(User(
            email = email,
            name = name,
            password = encodedPassword,
            roles = role,
            instructorProfile = instructor,
            participantProfile = participant,
        ))
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