package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.dto.InstructorDto
import com.wafflestudio.seminar.domain.user.dto.ParticipantDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigInteger
import java.time.LocalDateTime
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
        val instructors: InstructorDto.Response,
    //  participants
        val participants: ParticipantDto.Response?
    ) {
        constructor(seminar: Seminar) : this(
            id = seminar.id,
            name = seminar.name,
            capacity = seminar.capacity,
            count = seminar.count,
            time = seminar.time,
            online = seminar.online,
            instructors = seminar.instructors
        )
    }

    data class ResponseForSeminarOfParticipant(
        val id: Long,
        val name: String,
        val joinedAt: LocalDateTime,
        val isActive: Boolean,
        val droppedAt: LocalDateTime?,
    ) {
        constructor(seminar: Seminar, seminarParticipant: SeminarParticipant): this(
            id = seminar.id,
            name = seminar.name,
            joinedAt = seminarParticipant.joinedAt,
            isActive = seminarParticipant.isActive,
            droppedAt = seminarParticipant.droppedAt
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