package com.wafflestudio.seminar.domain.seminar.api

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.global.auth.CurrentUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/seminars")
class SeminarController (
    private val seminarService: SeminarService,
    private val userRepository: UserRepository,
) {
    // @CurrentUser 를 사용하여 현재 user 가 instructor 의 권한이 있는 경우
    // post seminar 가 가능해진다.
    // service 단에서 CurrentUser 의 role 이 instructor 인지 파악하자.
    @PostMapping("/")
    fun createSeminar(
        @CurrentUser user: User,
        @Valid @RequestBody seminarRequest: SeminarDto.Request
    ): ResponseEntity<SeminarDto.Response> {
        val newSeminar = seminarService.saveSeminar(user, seminarRequest)
        return ResponseEntity(SeminarDto.Response(newSeminar, userRepository), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/")
    fun getInfoThisSeminar(@PathVariable("id") id: Long): ResponseEntity<SeminarDto.Response> {
        val seminarResponse = seminarService.getSeminarResponseId(id)
        return ResponseEntity(SeminarDto.Response(seminarResponse, userRepository), HttpStatus.OK)
    }
}