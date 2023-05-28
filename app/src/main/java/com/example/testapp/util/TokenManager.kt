package com.example.testapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.testapp.util.Constants.PREFS_TOKEN_FILE
import com.example.testapp.util.Constants.USER_DETAILS
import com.example.testapp.util.Constants.USER_TOKEN
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    public var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)


    /** Save token **/

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun removeData(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    /** save user details **/

    fun <T> saveUserDetails(details: T?){
        val jsonString = GsonBuilder().create().toJson(details)
        val editor = prefs.edit()
        editor.putString(USER_DETAILS,jsonString).apply()
    }

    inline fun <reified T> getUserDetails(): T? {
        val value = prefs.getString(USER_DETAILS, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

}