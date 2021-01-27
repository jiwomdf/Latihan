package com.katilijiwo.latihan.data

import com.katilijiwo.latihan.base.BaseRepository
import com.katilijiwo.latihan.data.remote.json.UsersResponse
import com.katilijiwo.latihan.data.remote.service.UsersService
import com.katilijiwo.latihan.util.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val usersService: UsersService
): BaseRepository(), Repository {

    override suspend fun fetchUsers(query: String, page: String, per_page: String): UsersResponse {
        return when(val result = createCall { usersService.fetchUsers(query, page, per_page) }){
            is Resource.Success -> result.data
            is Resource.Error -> throw result.exception
        }
    }

}