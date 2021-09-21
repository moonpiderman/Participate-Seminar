package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class UserRoleException(detail: String="") :
        InvalidRequestException(ErrorType.USER_ROLE_NEITHER_PARTICIPANT_OR_INSTRUCTOR, detail)