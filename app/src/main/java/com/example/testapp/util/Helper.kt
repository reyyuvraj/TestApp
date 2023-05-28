package com.example.testapp.util

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.regex.Pattern

class Helper {
    companion object {

        private val mobileNumberPattern = Pattern.compile("^[0-9]{8,20}$")
        private val namePattern = Pattern.compile("^[\\p{L} .'-]+$")

        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidMobile(mobileNumber: String): Boolean {
            if (!TextUtils.isEmpty(mobileNumber) && mobileNumberPattern.matcher(mobileNumber)
                    .matches()
            ) {
                return true
            }
            return false
//            return !TextUtils.isEmpty(mobileNumber) && Patterns.PHONE.matcher(mobileNumber).matches()
        }

        fun containsSpecialCharacter(text: String): Boolean {
            val regex = Regex("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")
            return !text.matches(regex)
        }

        fun isNameValid(name: String): Boolean {
            if (!TextUtils.isEmpty(name) && namePattern.matcher(name).matches()) {
                return true
            }
            return false
        }

        fun hideKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }
        }
    }
}