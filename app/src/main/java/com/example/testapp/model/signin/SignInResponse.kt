package com.example.testapp.model.signin

data class SignInResponse(
    val token: String,
    val user: List<User>
)