package com.katilijiwo.latihan.data.remote.exception

import java.io.IOException

const val USER_AUTHORIZED = "User Unauthorized"
class UnAuthorizedException: IOException() {
    override val message: String = USER_AUTHORIZED
}