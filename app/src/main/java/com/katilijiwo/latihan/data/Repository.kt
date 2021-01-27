package com.katilijiwo.latihan.data

import com.katilijiwo.latihan.data.remote.json.UsersResponse
import com.katilijiwo.latihan.util.Resource

interface Repository {
    suspend fun fetchUsers(query: String, page: String, per_page: String): UsersResponse
}