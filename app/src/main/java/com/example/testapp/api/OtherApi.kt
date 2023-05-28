package com.example.testapp.api

import com.example.testapp.model.signup.SignUpRequest
import com.example.testapp.model.signup.SignUpResponse
import com.example.testapp.model.userdetails.UserDetailsRequest
import com.example.testapp.model.userdetails.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OtherApi {

    @POST("details")
    suspend fun userDetails(@Body userDetailsRequest: UserDetailsRequest): Response<UserDetailsResponse>

}