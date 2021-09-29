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
    val name: String,

    @Min(1)
    val capacity: Long,

    @Min(1)
    val count: Long,

    @field:NotBlank
    val time: LocalTime,

    val online: Boolean = true,

    @CreatedDate
    @Column(name = "joined_at")
    val joinedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "seminar", cascade = [CascadeType.ALL])
    val instructors: MutableList<InstructorProfile> = mutableListOf(),

    @OneToMany(mappedBy = "id", cascade = [CascadeType.ALL])
    val seminarParticipant: MutableList<SeminarParticipant> = mutableListOf()
) : BaseTimeEntity() {
    public fun addInstructor(instructorProfile: InstructorProfile) {
        instructors.add(instructorProfile)
    }
    public fun addParticipant(seminarParticipants: SeminarParticipant) {
        seminarParticipant.add(seminarParticipants)
    }
}