package com.katilijiwo.latihan.data

import androidx.paging.PagingSource
import com.katilijiwo.latihan.data.remote.json.Item
import com.katilijiwo.latihan.util.Constant.Companion.GITHUB_API_STARTING_PAGE
import com.katilijiwo.latihan.util.Constant.Companion.PAGER_MAX_SIZE
import java.lang.Exception

const val RESPONSE_ITEMS_NULL_MSG = "response items is null"
const val RESPONSE_TOTAL_COUNT_NULL_MSG = "response total count is null"
class UsersPageSource(
    private val repository: Repository,
    private val query: String
): PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val startingPage = GITHUB_API_STARTING_PAGE
        val position = params.key ?: startingPage
        val perPage = PAGER_MAX_SIZE
        return try {
            val response = repository.fetchUsers(query, position.toString(), perPage.toString())
            if(response?.items != null){
                LoadResult.Page(
                    data = response.items,
                    prevKey = if(position == startingPage) null else position -1,
                    nextKey = if(response.items.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(NullPointerException(RESPONSE_ITEMS_NULL_MSG))
            }
        } catch (ex: Exception){
            LoadResult.Error(ex)
        }
    }

}