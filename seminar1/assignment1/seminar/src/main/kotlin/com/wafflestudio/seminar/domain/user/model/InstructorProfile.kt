package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import javax.persistence.*

@Entity
@Table(name = "instructor")
class InstructorProfile (
    @Column
    var company: String? = "",

    @Column
    var year: Int? = null,

    @OneToOne(mappedBy = "instructorProfile")
    val user: User? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch= FetchType.LAZY, optional = true)
    var seminar: Seminar? = null,
) : BaseTimeEntity()