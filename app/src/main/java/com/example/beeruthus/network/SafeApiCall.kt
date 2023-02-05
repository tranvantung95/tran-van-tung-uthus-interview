package com.example.beeruthus.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend inline fun <Model> safeApiCall(
    crossinline callFunction: suspend () -> Model?
): AppResult<Model>? = try {
    val response = withContext(Dispatchers.IO) { callFunction.invoke() }
    if (response is Response<*>) {
        if (response.errorBody() != null) {
            throw HttpException(response)
        } else {
            AppResult.Success(response)
        }
    } else {
        AppResult.Success(response)
    }
} catch (e: Exception) {
    withContext(Dispatchers.Main) {
        e.printStackTrace()
        when (e) {
            is HttpException -> {
                val exception = getHttpException(e)
                AppResult.Error(exception)
            }
            else -> {
                AppResult.Error(UnspecifiedErrorException())
            }
        }
    }
}

fun getHttpException(exception: HttpException): Throwable {
    return Throwable(message = exception.message, cause = exception.cause)
}

class UnspecifiedErrorException : IOException("Something wrong happened")