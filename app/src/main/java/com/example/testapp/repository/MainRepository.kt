package com.example.testapp.repository

import com.example.testapp.api.OtherApi
import com.example.testapp.model.signup.SignUpRequest
import com.example.testapp.model.userdetails.UserDetailsRequest
import javax.inject.Inject

class MainRepository @Inject constructor(private val otherApi: OtherApi) {

    suspend fun userDetails(userDetailsRequest: UserDetailsRequest) = otherApi.userDetails(userDetailsRequest)

}