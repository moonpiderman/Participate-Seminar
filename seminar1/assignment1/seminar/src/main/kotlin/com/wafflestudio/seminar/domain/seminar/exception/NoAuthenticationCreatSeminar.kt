package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class NoAuthenticationCreatSeminar(detail: String="") :
        NotAllowedException(ErrorType.INVALID_USER, detail)