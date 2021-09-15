package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.common.exception.WaffleException

class UserNotFoundException : WaffleException("Err Param")

class UserEmailDuplicate : WaffleException("Duplicated Email")