package com.example.testapp.api

import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.model.signin.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("signin")
    suspend fun signIn(@Body authRequestSignup: SignInRequest): Response<SignInResponse>

}