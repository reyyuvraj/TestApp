package com.example.testapp.repository

import com.example.testapp.api.OtherApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val otherApi: OtherApi) {
}