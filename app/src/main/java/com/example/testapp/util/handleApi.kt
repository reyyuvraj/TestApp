package com.example.testapp.util

import com.example.testapp.model.ErrorResponse
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            return if (response.errorBody() != null) {
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse =
                    GsonSingleton().fromJson(response.errorBody()!!.string(), type)
                NetworkResult.Error(code = response.code(), message = errorResponse.response_message)
            } else {
                NetworkResult.Error(code = response.code(), message = response.message())
            }

        }
    }  catch (e: HttpException) {

        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {

        NetworkResult.Exception(e)
    }
}