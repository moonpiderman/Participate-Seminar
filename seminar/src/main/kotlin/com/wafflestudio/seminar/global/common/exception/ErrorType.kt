package com.wafflestudio.seminar.global.common.exception

enum class ErrorType (
    val code: Int
) {
    INVALID_REQUEST(0),

    NOT_ALLOWED(3000),

    DATA_NOT_FOUND(4000),
    SURVEY_RESPONSE_NOT_FOUND(4001),
    OS_NOT_FOUND(4002),
    USER_NOT_FOUND(4003),
    INVALID_USER(4004),
    SEMINAR_NOT_FOUND(4005),
    INVALID_YEAR(4006),
    ROLE_NOT_FOUND(4007),
    ALREADY_PARTICIPANT(4008),
    ALREADY_JOIN_AS_INSTRUCTOR(4009),
    CAPACITY_OVER(4010),
    UNAUTHORIZED_FOR_PARTICIPATE(4011),
    INSTRUCTOR_NOT_ALLOWED_DROP_SEMINAR(4012),
    UNAUTHORIZED_FOR_MODIFY_SEMINAR(4013),
    NO_PARTICIPATE_AS_PARTICIPANT(4014),
    CANNOT_RE_JOIN_SEMINAR(4015),

    CONFLICT(9000),
    USER_ALREADY_EXISTS(9001),

    SERVER_ERROR(10000)
}