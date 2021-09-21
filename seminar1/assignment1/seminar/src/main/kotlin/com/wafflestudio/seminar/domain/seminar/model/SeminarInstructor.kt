package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import javax.persistence.*

@Entity
@Table(name = "seminar_instructor")
class SeminarInstructor (
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val seminar: Seminar,

    // InstructorProfile 에서 매핑해줄 부분이 필요하다.
    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "seminarInstructor")
    val instructorProfile: List<InstructorProfile> = listOf()

) : BaseEntity()