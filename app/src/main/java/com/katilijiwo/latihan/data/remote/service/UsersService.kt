package com.katilijiwo.latihan.data.remote.service

import com.katilijiwo.latihan.data.remote.json.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {

    //https://api.github.com/search/users?q=test&page=11&per_page=10
    @GET("search/users")
    suspend fun fetchUsers(
        @Query("q") query: String,
        @Query("page") page: String,
        @Query("per_page") per_page: String
    ): Response<UsersResponse>
}