package com.alfianyusufabdullah.chatyuk.presentation.authentication.register

import com.alfianyusufabdullah.chatyuk.data.entity.User

interface RegisterView {
    fun onUsernameEmpty()
    fun onEmailEmpty()
    fun onEmailInvalid()
    fun onPasswordEmpty()
    fun onPasswordToShort()
    fun onConfirmPasswordEmpty()
    fun onConfirmPasswordNotMatch()
    fun onRegisterStart()
    fun onRegisterSuccess(user: User)
    fun onRegisterFailed(error: String?)
}