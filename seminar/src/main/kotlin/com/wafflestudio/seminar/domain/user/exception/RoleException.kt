package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class RoleException(detail: String = "") :
        InvalidRequestException(ErrorType.ROLE_NOT_FOUND, detail)