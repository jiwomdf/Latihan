package com.katilijiwo.latihan.base

import androidx.lifecycle.ViewModel
import com.katilijiwo.latihan.data.remote.json.Item

abstract class BaseViewModel: ViewModel() {
    sealed class UserEvent {
        class Success(val data: List<Item>): UserEvent()
        class Error(val exception: Exception): UserEvent()
        object Loading: UserEvent()
    }
}