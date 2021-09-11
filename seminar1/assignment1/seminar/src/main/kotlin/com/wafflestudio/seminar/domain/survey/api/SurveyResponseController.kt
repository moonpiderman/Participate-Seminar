package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.os.exception.OsNotFoundException
import com.wafflestudio.seminar.domain.os.service.OperatingSystemService
import com.wafflestudio.seminar.domain.survey.exception.SurveyNotFoundException
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.repository.SurveyResponseRepository
import com.wafflestudio.seminar.domain.survey.service.SurveyResponseService
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.service.UserService
import org.apache.catalina.User
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class SurveyResponseController(
    private val surveyResponseService: SurveyResponseService,
    private val surveyResponseRepository: SurveyResponseRepository,
    private val operatingSystemService: OperatingSystemService,
    private val userService: UserService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")
    fun getSurveyResponses(@RequestParam(required = false) os: String?): ResponseEntity<List<SurveyResponseDto.Response>> {
        return try {
            val surveyResponses =
                if (os != null) surveyResponseService.getSurveyResponsesByOsName(os)
                else surveyResponseService.getAllSurveyResponses()
            val responseBody = surveyResponses.map { modelMapper.map(it, SurveyResponseDto.Response::class.java) }
            ResponseEntity.ok(responseBody)
        } catch (e: OsNotFoundException) {
            ResponseEntity.notFound().build()
        }
        // AOP를 적용해 exception handling을 따로 하도록 고쳐보셔도 됩니다.
    }

    @GetMapping("/{id}/")
    fun getSurveyResponse(@PathVariable("id") id: Long): ResponseEntity<SurveyResponseDto.Response> {
        return try {
            val surveyResponse = surveyResponseService.getSurveyResponseById(id)
            val responseBody = modelMapper.map(surveyResponse, SurveyResponseDto.Response::class.java)
            ResponseEntity.ok(responseBody)
        } catch (e: SurveyNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/")
    fun addSurveyResponse(
        @ModelAttribute @Valid body: SurveyResponseDto.CreateRequest, bindingResult: BindingResult,
        @RequestHeader("User-Id") userId: Long
//    ): SurveyResponseDto.Response {
    ): ResponseEntity<SurveyResponse> {
        // TODO: status code 받을 수 있게끔 API 생성
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build()
        }
        return try {
            val newSurvey = SurveyResponse(
                os = body.os,
                springExp = body.springExp,
                rdbExp = body.rdbExp,
                programmingExp = body.programmingExp,
                major = body.major,
                grade = body.grade,
                backendReason = body.backendReason,
                waffleReason = body.waffleReason,
                somethingToSay = body.somethingToSay,
                user = userService.getUserInfoByUserId(userId)
            )
            surveyResponseRepository.save(newSurvey)
            ResponseEntity(newSurvey, HttpStatus.CREATED)
        } catch (e: OsNotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: UserNotFoundException) {
            ResponseEntity.notFound().build()
        }


        //TODO: API 생성
//        val newSurvey = SurveyResponse(
//            os = body.os, springExp = body.springExp, rdbExp = body.rdbExp, programmingExp = body.programmingExp,
//            major = body.major, grade = body.grade, backendReason = body.backendReason, waffleReason = body.waffleReason,
//            somethingToSay = body.somethingToSay
//        )
//        surveyResponseRepository.save(newSurvey)
//        // 이 사이에 spring rdb programming에 관한 범위 제한을 넣어줘야하나? return try?
//        // 201 status code도 삽입해주어야함.
//        return SurveyResponseDto.Response(id = newSurvey.id, os = newSurvey.os, springExp = newSurvey.springExp, rdbExp = newSurvey.rdbExp,
//        programmingExp = newSurvey.programmingExp, major = newSurvey.major, grade = newSurvey.grade, backendReason = newSurvey.backendReason,
//        waffleReason = newSurvey.waffleReason, somethingToSay = newSurvey.somethingToSay, timestamp = newSurvey.timestamp)
    }

}
