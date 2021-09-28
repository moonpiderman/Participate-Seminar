package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.dto.SeminarParticipantDto
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val name: String,
        val date_joined: LocalDateTime,
        // participant_profile
        val participant_profile: ParticipantDto.Response?,
        // instructor_profile
        val instructor_profile: InstructorDto.Response?
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name,
            date_joined = user.date_joined,
            participant_profile = user.participantProfile?.let {ParticipantDto.Response(it)},
            instructor_profile = user.instructorProfile?.let { InstructorDto.Response(it) }

        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String,
        @field:NotBlank
        val role: String,

        @field:NotNull
        val university: String? = "",
        @field:NotNull
        val accepted: Boolean? = true,

        @field:NotNull
        val company: String? = "",
        @field:NotNull
        val year: Int? = 0
    )

    data class ModifyRequest(
        @field:NotNull
        val email: String,

        @field:NotNull
        val name: String,

        @field:NotNull
        val password: String,

//        @field:NotNull
        val university: String? = null,

//        @field:NotNull
        val company: String? = null,

//        @field:NotNull
        val year: Int? = 0,

//        @field:NotNull
        var accepted: String?,
    )
}