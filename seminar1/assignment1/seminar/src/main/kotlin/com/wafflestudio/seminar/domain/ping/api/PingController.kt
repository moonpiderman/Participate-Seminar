package com.wafflestudio.seminar.domain.ping.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController() {
    @GetMapping("/ping/")
    fun pingRequest(): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.OK)
    }
}