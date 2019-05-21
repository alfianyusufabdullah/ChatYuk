package com.alfianyusufabdullah.chatyuk.presentation.authentication.register

import android.view.View
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepository
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepositoryCallback
import com.alfianyusufabdullah.chatyuk.isEmailValid

class RegisterPresenter(private val authenticationRepository: AuthenticationRepository) {

    private var view: RegisterView? = null

    fun attachView(view: RegisterView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun doRegister(user: User) {
        if (user.username?.isEmpty() == true) {
            view?.onUsernameEmpty()
            return
        }

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

        if (user.password?.length ?: 0 < 6) {
            view?.onPasswordToShort()
            return
        }

        if (user.confirmPassword?.isEmpty() == true) {
            view?.onConfirmPasswordEmpty()
            return
        }

        if (user.confirmPassword != user.password) {
            view?.onConfirmPasswordNotMatch()
            return
        }

        view?.onRegisterStart()
        view?.onProgress(View.GONE)

        authenticationRepository.doRegister(user, object : AuthenticationRepositoryCallback {
            override fun onSuccess(user: User) {
                view?.onRegisterSuccess(user)
                view?.onProgress(View.VISIBLE)
            }

            override fun onFailed(error: String?) {
                view?.onRegisterFailed(error)
                view?.onProgress(View.VISIBLE)
            }
        })
    }
}