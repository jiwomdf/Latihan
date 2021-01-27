package com.katilijiwo.latihan.data.remote.exception

import java.io.IOException

const val NOT_FOUND = "Not Found"
class NotFoundException: IOException() {
    override val message: String = NOT_FOUND
}