package com.wafflestudio.seminar.domain.seminar.api

import com.wafflestudio.seminar.domain.seminar.dto.GetSeminarInfoDto
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.service.SeminarService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/seminars")
class SeminarController (
    private val seminarService: SeminarService,
    private val userRepository: UserRepository,
    private var listOfSeminar: List<GetSeminarInfoDto.Response>,
) {
    @PostMapping("/")
    fun createSeminar(
        @CurrentUser user: User,
        @Valid @RequestBody seminarRequest: SeminarDto.Request
    ): ResponseEntity<SeminarDto.Response> {
        val newSeminar = seminarService.saveSeminar(user, seminarRequest)
        return ResponseEntity(SeminarDto.Response(newSeminar, userRepository), HttpStatus.CREATED)
    }

    @GetMapping("/{id}/")
    fun getInfoThisSeminar(
        @PathVariable("id") id: Long
    ): ResponseEntity<SeminarDto.Response> {
        val seminarResponse = seminarService.getSeminarResponseId(id)
        return ResponseEntity(SeminarDto.Response(seminarResponse, userRepository), HttpStatus.OK)
    }

    @GetMapping("/")
    fun getSeminarsInfo(
        @RequestParam(value="name", required = false) seminarName: String?,
        @RequestParam(value="order", required = false) seminarOrder: String?
    ): ResponseEntity<ListResponse<GetSeminarInfoDto.Response>> {
        if (seminarName != null) {
            if (seminarOrder != null && seminarOrder == "earliest") {
                listOfSeminar = seminarService.getSeminarInfoByName(seminarName, false)
            } else {
                listOfSeminar = seminarService.getSeminarInfoByName(seminarName, true)
            }
        } else {
            if (seminarOrder != null && seminarOrder == "earliest") {
                listOfSeminar = seminarService.getAllSeminarInfo(false)
            } else {
                listOfSeminar = seminarService.getAllSeminarInfo(true)
            }
        }
        return ResponseEntity(ListResponse(listOfSeminar), HttpStatus.OK)
    }

    @PutMapping("/{id}/")
    fun modifySeminar(
        @PathVariable("id") id: Long,
        @Valid @RequestBody modifyRequest: SeminarDto.Request?,
        @CurrentUser currentUser: User
    ): ResponseEntity<SeminarDto.Response> {
        val editedSeminar = seminarService.modifySeminar(id, modifyRequest, currentUser)
        return ResponseEntity(SeminarDto.Response(editedSeminar, userRepository), HttpStatus.OK)
    }

    @PostMapping("/{id}/user/")
    fun joinSeminar(
        @PathVariable("id") id: Long,
        @CurrentUser currentUser: User,
        @Valid @RequestBody joinRequest: SeminarDto.JoinRequest,
    ): ResponseEntity<SeminarDto.SeminarTotalResponse> {
        seminarService.participateSeminar(id, joinRequest, currentUser)
        val newSeminar = seminarService.getSeminarResponseId(id)
        return ResponseEntity(SeminarDto.SeminarTotalResponse(newSeminar), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}/user/me/")
    fun giveUpSeminar(
        @PathVariable("id") id: Long,
        @CurrentUser user: User,
    ): ResponseEntity<SeminarDto.SeminarTotalResponse> {
        val dropThisSeminar = seminarService.dropSeminar(id, user)
        return ResponseEntity(SeminarDto.SeminarTotalResponse(dropThisSeminar), HttpStatus.OK)
    }
}