package com.example.myonlinestore.network

import com.example.myonlinestore.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.HttpException

 open class SafeApiCall{
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
             try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        NetworkResult.Success(body)
                    } else {
                        NetworkResult.Error("Empty response body")
                    }
                } else {
                    NetworkResult.Error(message = "Something went wrong")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "An error occurred")
            }
        }
    }
}