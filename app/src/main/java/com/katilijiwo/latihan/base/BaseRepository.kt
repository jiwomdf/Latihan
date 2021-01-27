package com.katilijiwo.latihan.base

import com.katilijiwo.latihan.data.remote.exception.*
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.katilijiwo.latihan.util.Resource

const val CONNECTION_TIME_OUT = "Connection Timed Out"
abstract class BaseRepository {

    protected suspend fun<T: Any> createCall(call: suspend () -> Response<T>) : Resource<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable){
            t.printStackTrace()
            return Resource.Error(mapToNetworkError(t))
        }

        if (response.isSuccessful){
            if (response.body() != null){
                return Resource.Success(response.body()!!)
            }
        }
        else{
            val errorBody = response.errorBody()
            return if (errorBody != null){
                Resource.Error(mapApiException(response.code()))
            } else {
                Resource.Error(mapApiException(0))
            }
        }

        return Resource.Error(HttpException(response))
    }

    private fun mapToNetworkError(t: Throwable): Exception {
        return when(t){
            is SocketTimeoutException -> SocketTimeoutException(CONNECTION_TIME_OUT)
            is UnknownHostException -> NoInternetException()
            else -> UnKnownException()
        }
    }

    private fun mapApiException(code: Int): Exception {
        return when(code){
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnAuthorizedException()
            HttpURLConnection.HTTP_FORBIDDEN -> ForbiddenException()
            else -> UnKnownException()
        }
    }


}