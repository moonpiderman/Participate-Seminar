package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.user.dto.InstructorsProfileForSeminarDto
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SeminarDto {
    data class Response(
        val id: Long,
        val name: String,
        val capacity: Long,
        val count: Long,
        val time: LocalTime,
        val online: Boolean? = true,
    //  instructors
        val instructors: List<InstructorsProfileForSeminarDto.Response>,
    //  participants
        val participants: List<SeminarParticipantDto.ResponseForSeminarParticipants>,
    ) {
        constructor(seminar: Seminar, userRepository: UserRepository) : this(
            id = seminar.id,
            name = seminar.name,
            capacity = seminar.capacity,
            count = seminar.count,
            time = seminar.time,
            online = seminar.online,
            instructors = seminar.instructors.map {
                    it -> InstructorsProfileForSeminarDto.Response(userRepository.findUserByEmail(it.user!!.email))
            },
            participants = seminar.seminarParticipant.map {it ->
                SeminarParticipantDto.ResponseForSeminarParticipants(
                    userRepository.findUserByEmail(it.participantProfile.user!!.email), it)
            }
        )
    }

    data class Request(
        @field:NotBlank
        val name: String,

        @field:NotNull
        @Min(1)
        val capacity: Long,

        @field:NotNull
        @Min(1)
        val count: Long,

        @field:NotNull
        @field:DateTimeFormat(pattern = "HH:mm")
        val time: LocalTime,

        @field:NotNull
        val online: Boolean?
    )
}