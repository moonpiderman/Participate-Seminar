package com.wafflestudio.seminar.domain.seminar.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class CannotJoinDroppingSeminar(detail: String = "") :
        NotAllowedException(ErrorType.CANNOT_RE_JOIN_SEMINAR, detail)