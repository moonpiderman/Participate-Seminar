package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class UserRoleException(detail: String = "") :
        InvalidRequestException(ErrorType.INVALID_USER, detail)