package com.example.testapp.util

import com.google.gson.Gson

/* Use this class to provide gson singleton instance to handle api function because we can not inject their by hilt*/
class GsonSingleton private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: Gson? = null

        @Synchronized
        operator fun invoke(): Gson = INSTANCE ?: Gson().also { INSTANCE = it }
    }
}