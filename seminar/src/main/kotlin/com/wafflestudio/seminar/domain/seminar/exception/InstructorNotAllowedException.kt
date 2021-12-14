package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class InstructorNotAllowedException(detail: String = "") :
        NotAllowedException(ErrorType.INSTRUCTOR_NOT_ALLOWED_DROP_SEMINAR, detail)