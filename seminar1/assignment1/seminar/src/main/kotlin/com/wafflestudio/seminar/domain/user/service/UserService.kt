package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import jdk.jshell.spi.ExecutionControl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Autowired
    fun getAllUserResponse(): List<User> {
        return userRepository.findAll()
    }

    fun getUserInfoByUserId(id : Long): User? {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}