package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class NoAuthenticationModifySeminar(detail: String = ""):
        NotAllowedException(ErrorType.UNAUTHORIZED_FOR_MODIFY_SEMINAR, detail)