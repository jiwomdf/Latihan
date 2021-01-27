package com.katilijiwo.latihan.data.remote.exception

import java.io.IOException

const val NO_INTERNET_CONNECTION = "No Internet connection"
class NoInternetException: IOException() {
    override val message: String
        get() = NO_INTERNET_CONNECTION
}