package com.example.testapp.repository

import com.example.testapp.api.AuthApi
import com.example.testapp.model.signin.SignInRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    suspend fun signIn(authRequestSignup: SignInRequest) = authApi.signIn(authRequestSignup)

}