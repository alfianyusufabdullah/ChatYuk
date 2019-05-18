package com.alfianyusufabdullah.chatyuk.presentation.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alfianyusufabdullah.chatyuk.ChatApplication
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepository
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepository
import com.alfianyusufabdullah.chatyuk.presentation.authentication.AuthenticationActivity
import com.alfianyusufabdullah.chatyuk.presentation.authentication.AuthenticationPageListener
import com.alfianyusufabdullah.chatyuk.common.InputTextListener
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginView {

    private lateinit var pageListener: AuthenticationPageListener
    private lateinit var messageRepository: MessageRepository
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var loginPresenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageRepository = MessageRepository(ChatReferences())
        authenticationRepository = AuthenticationRepository(FirebaseAuth.getInstance(), ChatReferences())

        loginPresenter = LoginPresenter(authenticationRepository)
        loginPresenter.attachView(this)

        loginEmail.addTextChangedListener(InputTextListener(inputEmail))
        loginPass.addTextChangedListener(InputTextListener(inputPass))

        txtSignUp.setOnClickListener { pageListener.onRegisterPage() }
        btnSignIn.setOnClickListener {

            val user = User().apply {
                email = loginEmail.text.toString().trim()
                password = loginPass.text.toString().trim()
            }

            loginPresenter.doLogin(user)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        pageListener = context as AuthenticationPageListener
    }

    override fun onEmailEmpty() {
        inputEmail.error = "Masukkan email"
        loginEmail.requestFocus()
    }

    override fun onEmailInvalid() {
        inputEmail.error = "Invalid email"
        loginPass.requestFocus()
    }

    override fun onPasswordEmpty() {
        inputPass.error = "Masukkan password"
        loginPass.requestFocus()
    }

    override fun onLoginStart() {
        ChatApplication.hideSoftInput(context as AuthenticationActivity, loginEmail)

        btnSignIn.isEnabled = false
        setInputTextEnabled(false)
    }

    override fun onLoginSuccess(user: User) {
        pageListener.onAuthenticateSuccess(user)
    }

    override fun onLoginFailed(error: String?) {
        showSnackbar(error)

        btnSignIn.isEnabled = true
        setInputTextEnabled(true)
    }

    private fun setInputTextEnabled(state: Boolean) {
        inputEmail.isEnabled = state
        inputPass.isEnabled = state

        loading.visibility = if (state) View.GONE else View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        loginPresenter.detachView()
    }

    private fun showSnackbar(message: String?) {
        Snackbar.make(rootView, message ?: "empty", Snackbar.LENGTH_SHORT).show()
    }
}