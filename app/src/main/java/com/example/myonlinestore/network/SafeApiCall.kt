package com.example.myonlinestore.network

import com.example.myonlinestore.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResult.Error(throwable.response()?.errorBody().toString(), null)
                    }
                    else -> {
                        NetworkResult.Error(throwable.localizedMessage, null)
                    }
                }
            }
        }
    }
}