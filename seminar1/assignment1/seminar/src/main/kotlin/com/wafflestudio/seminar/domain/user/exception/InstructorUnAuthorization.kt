package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class InstructorUnAuthorization(detail: String = ""):
        NotAllowedException(ErrorType.UNAUTHORIZED_FOR_MODIFY_SEMINAR, detail)