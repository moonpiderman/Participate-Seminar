package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.Seminar
//import com.wafflestudio.seminar.domain.seminar.model.SeminarInstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "instructor")
class InstructorProfile (
    @Column
    var company: String? = "",

    @Column
    var year: Int? = null,

    // charge
    // 담당하는 세미나

    // user_id에 관한 필드가 존재해야한다.
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne(cascade = [CascadeType.MERGE], fetch= FetchType.EAGER, optional = true)
    var seminar: Seminar? = null,
) : BaseTimeEntity()