package com.katilijiwo.latihan.data.remote.exception

import java.io.IOException

const val SOME_UNKNOWN_ERROR_OCCURRED = "Some unknown error occurred"
class UnKnownException: IOException() {
    override val message: String
        get() = SOME_UNKNOWN_ERROR_OCCURRED
}