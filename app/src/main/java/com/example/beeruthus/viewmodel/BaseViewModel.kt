package com.example.beeruthus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beeruthus.network.AppResult
import com.example.beeruthus.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    val errorMessage = SingleLiveEvent<Throwable>()
    val isLoading = SingleLiveEvent<Boolean>()

    suspend fun <Model> safeModelCall(
        callFunction: suspend () -> AppResult<Model>?,
    ): Model? {
        loadingVisible()
        val appResultRes =
            withContext(Dispatchers.IO) { callFunction.invoke() } ?: return null
        if (appResultRes is AppResult.Success) {
            loadingInvisible()
            return appResultRes.data
        } else if (appResultRes is AppResult.Error) {
            loadingInvisible()
            errorMessage.value = appResultRes.error
            return null
        }
        return null
    }

    fun handleGeneralError(throwable: Throwable) {
        errorMessage.value = throwable
    }

    fun loadingVisible() {
        isLoading.value = true
    }

    fun loadingInvisible() {
        viewModelScope.launch {
            isLoading.value = false
        }
    }
}
