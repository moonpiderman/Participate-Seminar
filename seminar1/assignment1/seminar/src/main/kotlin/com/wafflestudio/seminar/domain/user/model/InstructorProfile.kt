package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarInstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "instructor")
class InstructorProfile (
    @Column
    val company: String? = "",

    @Column
    val year: Long? = 0,

    // charge
    // 담당하는 세미나

    @CreatedDate
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    // user_id에 관한 필드가 존재해야한다.
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(referencedColumnName = "id")
    val seminarInstructor: SeminarInstructor? = null

) : BaseEntity()