package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class AlreadyJoinSeminarAsInstructor(detail: String = ""):
        NotAllowedException(ErrorType.ALREADY_JOIN_AS_INSTRUCTOR, detail)