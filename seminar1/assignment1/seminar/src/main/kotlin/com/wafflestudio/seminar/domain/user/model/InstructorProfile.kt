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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne(cascade = [CascadeType.MERGE], fetch= FetchType.EAGER, optional = true)
    var seminar: Seminar? = null,
) : BaseTimeEntity()