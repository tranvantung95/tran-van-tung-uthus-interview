package com.example.beeruthus.network

sealed class AppResult<T> {

    data class Success<T>(val data: T?) : AppResult<T>()

    data class Error<T>(val error: Throwable) : AppResult<T>()
}
