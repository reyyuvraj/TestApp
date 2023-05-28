package com.example.testapp.repository

import com.example.testapp.api.AuthApi
import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.model.signup.SignUpRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    suspend fun signIn(authRequestSignin: SignInRequest) = authApi.signIn(authRequestSignin)

    suspend fun signUp(authRequestSignup: SignUpRequest) = authApi.signUp(authRequestSignup)

}