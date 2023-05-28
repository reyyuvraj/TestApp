package com.example.testapp.api

import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.model.signin.SignInResponse
import com.example.testapp.model.signup.SignUpRequest
import com.example.testapp.model.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("signin")
    suspend fun signIn(@Body authRequestSignin: SignInRequest): Response<SignInResponse>

    @POST("signup")
    suspend fun signUp(@Body authRequestSignup: SignUpRequest): Response<SignUpResponse>

}