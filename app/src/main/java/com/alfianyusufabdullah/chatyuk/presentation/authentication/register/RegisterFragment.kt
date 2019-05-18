package com.alfianyusufabdullah.chatyuk.presentation.authentication.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.common.InputTextListener
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepository
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepository
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.alfianyusufabdullah.chatyuk.hideSoftInput
import com.alfianyusufabdullah.chatyuk.presentation.authentication.AuthenticationPageListener
import com.alfianyusufabdullah.chatyuk.showSnackbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), RegisterView {

    private lateinit var pageListener: AuthenticationPageListener
    private lateinit var messageRepository: MessageRepository
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var registerPresenter: RegisterPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageRepository = MessageRepository(ChatReferences())
        authenticationRepository = AuthenticationRepository(FirebaseAuth.getInstance(), ChatReferences())

        registerPresenter = RegisterPresenter(authenticationRepository)
        registerPresenter.attachView(this)

        regisUser.addTextChangedListener(InputTextListener(inputUsername))
        regisEmail.addTextChangedListener(InputTextListener(inputEmail))
        regisPass.addTextChangedListener(InputTextListener(inputPass))
        regisPassConfirm.addTextChangedListener(InputTextListener(inputPassConfirm))

        txtSignIn.setOnClickListener { pageListener.onLoginPage() }
        btnSignUp.setOnClickListener {

            val user = User().apply {
                username = regisUser.text.toString().trim()
                email = regisEmail.text.toString().trim()
                password = regisPass.text.toString().trim()
                confirmPassword = regisPassConfirm.text.toString().trim()
            }

            registerPresenter.doRegister(user)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        pageListener = context as AuthenticationPageListener
    }

    override fun onUsernameEmpty() {
        inputUsername.error = "Masukkan username"
        regisUser.requestFocus()
    }

    override fun onEmailEmpty() {
        inputEmail.error = "Masukkan email"
        regisEmail.requestFocus()
    }

    override fun onEmailInvalid() {
        inputEmail.error = "Invalid email"
        regisEmail.requestFocus()
    }

    override fun onPasswordEmpty() {
        inputPass.error = "Masukkan password"
        regisPass.requestFocus()
    }

    override fun onPasswordToShort() {
        inputPass.error = "Password terlalu pendek"
        regisPass.requestFocus()
    }

    override fun onConfirmPasswordEmpty() {
        inputPassConfirm.error = "Masukkan konfirmasi password"
        regisPassConfirm.requestFocus()
    }

    override fun onConfirmPasswordNotMatch() {
        inputPassConfirm.error = "Password salah"
        regisPassConfirm.requestFocus()
    }

    override fun onRegisterStart() {
        context?.hideSoftInput(regisEmail)

        btnSignUp.isEnabled = false
        setInputTextEnabled(false)
    }

    override fun onRegisterSuccess(user: User) {
        pageListener.onAuthenticateSuccess(user)
    }

    override fun onRegisterFailed(error: String?) {
        rootView.showSnackbar(error)

        btnSignUp.isEnabled = true
        setInputTextEnabled(true)
    }

    private fun setInputTextEnabled(state: Boolean) {
        inputUsername.isEnabled = state
        inputEmail.isEnabled = state
        inputPass.isEnabled = state
        inputPassConfirm.isEnabled = state

        loading.visibility = if (state) View.GONE else View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        registerPresenter.detachView()
    }
}