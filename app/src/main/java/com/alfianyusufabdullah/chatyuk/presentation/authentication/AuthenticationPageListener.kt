package com.alfianyusufabdullah.chatyuk.presentation.authentication

import com.alfianyusufabdullah.chatyuk.data.entity.User

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

interface AuthenticationPageListener {
    fun onLoginPage()

    fun onRegisterPage()

    fun onAuthenticateSuccess(user: User)
}
