package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class AlreadyParticipantUser(detail: String = "") :
        InvalidRequestException(ErrorType.ALREADY_PARTICIPANT, detail)