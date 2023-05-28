package com.example.testapp.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ConverterUtil {

    companion object{
        fun parseString(str: String?): RequestBody {
            return str!!.toRequestBody("text/plain".toMediaTypeOrNull())
        }
    }

}