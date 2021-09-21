package com.wafflestudio.seminar.global.common.exception

enum class ErrorType (
    val code: Int
) {
    INVALID_REQUEST(0),

    NOT_ALLOWED(3000),

    DATA_NOT_FOUND(4000),
    SURVEY_RESPONSE_NOT_FOUND(4001),
    OS_NOT_FOUND(4002),

    CONFLICT(9000),
    USER_ALREADY_EXISTS(9001),
    USER_ROLE_NEITHER_PARTICIPANT_OR_INSTRUCTOR(9002),

    SERVER_ERROR(10000)
}
