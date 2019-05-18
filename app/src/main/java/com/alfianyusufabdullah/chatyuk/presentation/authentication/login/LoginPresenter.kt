package com.alfianyusufabdullah.chatyuk.presentation.authentication.login

import com.alfianyusufabdullah.chatyuk.common.Constant
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepository
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepositoryCallback
import com.alfianyusufabdullah.chatyuk.isEmailValid

class LoginPresenter(private val authenticationRepository: AuthenticationRepository) {

    private var view: LoginView? = null

    fun attachView(view: LoginView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun doLogin(user: User) {

        if (user.email?.isEmpty() == true) {
            view?.onEmailEmpty()
            return
        }

        if (user.email?.isEmailValid() == false) {
            view?.onEmailInvalid()
            return
        }

        if (user.password?.isEmpty() == true) {
            view?.onPasswordEmpty()
            return
        }

        view?.onLoginStart()

        authenticationRepository.doLogin(user, object : AuthenticationRepositoryCallback {
            override fun onSuccess(user: User) {
                view?.onLoginSuccess(user)
            }

            override fun onFailed(error: String?) {
                view?.onLoginFailed(error)
            }
        })
    }
}