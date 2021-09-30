package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar")
class Seminar (
    @field:NotBlank
    var name: String,

    @Min(1)
    var capacity: Long,

    @Min(1)
    var count: Long,

    @field:NotNull
    var time: LocalTime,

    var online: Boolean = true,

    @OneToMany(mappedBy = "seminar", cascade = [CascadeType.ALL])
    val instructors: MutableList<InstructorProfile> = mutableListOf(),

    @OneToMany(mappedBy = "seminar", cascade = [CascadeType.ALL])
    val seminarParticipant: MutableList<SeminarParticipant> = mutableListOf(),
) : BaseTimeEntity() {
    fun addInstructor(instructorProfile: InstructorProfile) {
        instructors.add(instructorProfile)
    }
    fun addParticipant(seminarParticipants: SeminarParticipant) {
        seminarParticipant.add(seminarParticipants)
    }
}