package com.wafflestudio.seminar.domain.survey.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SurveyResponseDto {
    data class Response(
        var id: Long?,
        var os: OperatingSystem?,
        var springExp: Int?,
        var rdbExp: Int?,
        var programmingExp: Int?,
        var major: String?,
        var grade: String?,
        var backendReason: String?,
        var waffleReason: String?,
        var somethingToSay: String?,
        var timestamp: LocalDateTime?
    ) {
        constructor(surveyResponse: SurveyResponse) : this(
            surveyResponse.id,
            surveyResponse.os,
            surveyResponse.springExp,
            surveyResponse.rdbExp,
            surveyResponse.programmingExp,
            surveyResponse.major,
            surveyResponse.grade,
            surveyResponse.backendReason,
            surveyResponse.waffleReason,
            surveyResponse.somethingToSay,
            surveyResponse.timestamp
        )
    }

    // TODO: 아래 두 DTO 완성
    data class CreateRequest(
        var id: Long?,
        @field:NotNull
        var os: String,
        @field:NotNull
        @field:Min(1)
        @field:Max(5)
        var springExp: Int?,
        @field:NotNull
        @field:Min(1)
        @field:Max(5)
        var rdbExp: Int?,
        @field:NotNull
        @field:Min(1)
        @field:Max(5)
        var programmingExp: Int?,
        var major: String?,
        var grade: String?,
        var backendReason: String?,
        var waffleReason: String?,
        var somethingToSay: String?,
        var timestamp: LocalDateTime?
    )
//    ) {
//        constructor(surveyResponse: SurveyResponse) : this(
//            surveyResponse.id,
//            surveyResponse.os,
//            surveyResponse.springExp,
//            surveyResponse.rdbExp,
//            surveyResponse.programmingExp,
//            surveyResponse.major,
//            surveyResponse.grade,
//            surveyResponse.backendReason,
//            surveyResponse.waffleReason,
//            surveyResponse.somethingToSay,
//            surveyResponse.timestamp
//        )
//    }

    data class ModifyRequest(
        var something: String? = ""
        // 예시 - 지우고 새로 생성
    )
}
