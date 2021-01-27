package com.katilijiwo.latihan.data.remote.exception

import java.io.IOException

const val USER_FORBIDDEN = "User Forbidden"
class ForbiddenException: IOException() {
    override val message: String = USER_FORBIDDEN
}