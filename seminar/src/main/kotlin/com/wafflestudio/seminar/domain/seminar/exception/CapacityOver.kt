package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class CapacityOver(detail: String = "") :
        InvalidRequestException(ErrorType.CAPACITY_OVER, detail)