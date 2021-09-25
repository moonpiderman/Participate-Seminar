package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
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
    val name: String,

    @field:NotBlank
    val password: String,

    // requset 시에만 사용하는 필드인가..? 아닐텐데...
    @Column
    @field:NotNull
    val roles: String,

    @Column
    @field:CreatedDate
    val date_joined: LocalDateTime = LocalDateTime.now(),

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    val instructorProfile: InstructorProfile? = null,

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    val participantProfiles: ParticipantProfile? = null
) : BaseEntity()
