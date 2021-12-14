package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @field:NotBlank
    val email: String,

    @field:NotBlank
    var name: String,

    @field:NotBlank
    var password: String,

    @Column
    @field:NotNull
    var roles: String = "",

    @Column
    @field:CreatedDate
    val date_joined: LocalDateTime = LocalDateTime.now(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var instructorProfile: InstructorProfile? = null,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var participantProfile: ParticipantProfile? = null
) : BaseTimeEntity()