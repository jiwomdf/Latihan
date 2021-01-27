package com.katilijiwo.latihan.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.katilijiwo.latihan.base.BaseViewModel
import com.katilijiwo.latihan.data.Repository
import com.katilijiwo.latihan.data.UsersPageSource
import com.katilijiwo.latihan.data.remote.json.Item
import com.katilijiwo.latihan.util.AbsentLiveData
import com.katilijiwo.latihan.util.Constant
import com.katilijiwo.latihan.util.Resource
import kotlinx.coroutines.launch
import java.lang.NullPointerException

const val RESPONSE_ITEMS_NULL_MSG = "response items is null"
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
): BaseViewModel() {

    private var _listUserByName = MutableLiveData<UserEvent>()
    val listUserByName: MutableLiveData<UserEvent> = _listUserByName
    fun fetchUserByName(name: String){
        viewModelScope.launch {
            try {
                _listUserByName.postValue(UserEvent.Loading)
                val response = repository.fetchUsers(name, "1", "10")
                if(response.items != null){
                    _listUserByName.postValue(UserEvent.Success(response.items))
                } else {
                    _listUserByName.postValue(UserEvent.Error(NullPointerException(RESPONSE_ITEMS_NULL_MSG)))
                }
            }
            catch (ex: Exception){
                _listUserByName.postValue(UserEvent.Error(ex))
            }
        }
    }

    private var currentQuery = MutableLiveData<String>()
    val users = Transformations.switchMap(currentQuery) { queryString ->
        if(queryString.isNullOrEmpty()){
            AbsentLiveData.create()
        } else {
            getSearchResult(queryString)
        }
    }
    fun getUsers(query: String){
        currentQuery.value = query
    }

    private fun getSearchResult(query: String): LiveData<PagingData<Item>>{
        return Pager(
            config = PagingConfig(
                pageSize = Constant.GITHUB_API_PER_PAGE,
                maxSize = Constant.PAGER_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UsersPageSource(repository, query)
            }
        ).liveData
    }

}