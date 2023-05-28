package com.example.testapp.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.repository.AuthRepository
import com.example.testapp.util.Helper
import com.example.testapp.util.handleApi
import com.example.testapp.util.onError
import com.example.testapp.util.onException
import com.example.testapp.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    private val app: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(app) {


    private val _signInUiState = MutableStateFlow(SignInScreenState())
    val signInUiState = _signInUiState.asStateFlow()

    fun signIn(authRequestSignup: SignInRequest) =
        viewModelScope.launch(Dispatchers.Main) {
            _signInUiState.update {
                it.copy(
                    loading = true
                )
            }
            handleApi {
                authRepository.signIn(authRequestSignup)
            }.onSuccess { response ->
                _signInUiState.update {
                    it.copy(
                        loading = false,
                        token = response.token
                    )
                }
            }.onError { code, message ->
                _signInUiState.update {
                    it.copy(
                        loading = false,
                        message = message
                    )
                }
            }.onException {
                _signInUiState.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }

    fun messageAlreadyDisplayed() {
        viewModelScope.launch {
            _signInUiState.update {
                it.copy(
                    message = null
                )
            }
        }
    }


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

data class SignInScreenState(
    val loading: Boolean = false,
    val message: String? = null,
    val token: String? = null
)