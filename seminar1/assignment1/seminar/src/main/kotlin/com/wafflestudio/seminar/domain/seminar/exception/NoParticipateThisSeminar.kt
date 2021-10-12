package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class NoParticipateThisSeminar(detail: String = "") :
        DataNotFoundException(ErrorType.NO_PARTICIPATE_AS_PARTICIPANT, detail)