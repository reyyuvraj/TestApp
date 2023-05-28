package com.example.testapp.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.testapp.util.Helper

class LandingScreenViewModel: ViewModel() {



    //--------------------------------------------------------------------------------------------------
    fun validateCredentialsLogin(emailAddress: String, password: String): Pair<Boolean, String> {

        var result = Pair(true, "")

        if (TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Helper.isValidEmail(emailAddress)) {
            result = Pair(false, "Email is invalid")
        } else if (!TextUtils.isEmpty(password) && password.length <= 7) {
            result = Pair(false, "Password length should be 8 characters long")
        }
        return result
    }

    fun validateCredentialsSignup(
        name: String, emailAddress: String,
        password: String, confirmPassword: String
    ): Pair<Boolean, String> {

        var result = Pair(true, "")

        if (TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(
                name
            ) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
        ) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Helper.isNameValid(name)) {
            result = Pair(false, "Name is invalid. It doesn't contain any number")
        } else if (!Helper.isValidEmail(emailAddress)) {
            result = Pair(false, "Email is invalid")
        } else if (!TextUtils.isEmpty(password) && password.length <= 7) {
            result = Pair(false, "Password length should be 8 characters long")
        } else if (password != confirmPassword) {
            result = Pair(false, "Password and Confirm password do not match")
        }

        return result
    }

}