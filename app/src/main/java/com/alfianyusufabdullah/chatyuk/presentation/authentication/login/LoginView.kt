package com.alfianyusufabdullah.chatyuk.presentation.authentication.login

import com.alfianyusufabdullah.chatyuk.data.entity.User

interface LoginView {
    fun onEmailEmpty()
    fun onEmailInvalid()
    fun onPasswordEmpty()
    fun onLoginStart()
    fun onLoginSuccess(user: User)
    fun onLoginFailed(error: String?)
}