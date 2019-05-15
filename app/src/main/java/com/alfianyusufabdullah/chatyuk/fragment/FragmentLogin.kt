package com.alfianyusufabdullah.chatyuk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alfianyusufabdullah.chatyuk.ActivityAuth
import com.alfianyusufabdullah.chatyuk.MyApplication
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.interfaces.AuthRequestListener
import com.alfianyusufabdullah.chatyuk.interfaces.AuthPageListener
import com.alfianyusufabdullah.chatyuk.model.ModelUser
import com.alfianyusufabdullah.chatyuk.network.AuthRequest
import com.alfianyusufabdullah.chatyuk.utils.Constant
import com.alfianyusufabdullah.chatyuk.utils.InputTextListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin : Fragment() {

    lateinit var registerPage: AuthPageListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginEmail.addTextChangedListener(InputTextListener(inputEmail))
        loginPass.addTextChangedListener(InputTextListener(inputPass))

        txtSignUp.setOnClickListener { registerPage.onRegisterPage() }

        btnSignIn.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPass.text.toString().trim()

            if (email.isEmpty()) {
                inputEmail.error = "Masukkan Email"
                loginEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Constant.isEmailValid(email)) {
                inputEmail.error = "Invalid Email"
                loginPass.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                inputPass.error = "Masukkan Password"
                loginPass.requestFocus()
                return@setOnClickListener
            }

            MyApplication.hideSoftInput(context as ActivityAuth, loginEmail)

            it.isEnabled = false
            setInputTextEnabled(false)

            AuthRequest.field(email = email, password = password)
                    .doLogin(object : AuthRequestListener {
                        override fun onAuthFailed(error: String) {

                            it.isEnabled = true
                            setInputTextEnabled(true)

                            when (error) {

                                Constant.ERR_EMAIL_NOT_EXISTS -> {
                                    inputEmail.error = "Email yang kamu masukkan sudah terdaftar"
                                    loginEmail.requestFocus()
                                }
                                Constant.ERR_PASS -> {
                                    inputPass.error = "Password yang kamu masukkan salah"
                                    loginPass.requestFocus()
                                }
                                else -> showSnackbar(error)
                            }

                        }

                        override fun onAuthSuccess(user: ModelUser) {
                            registerPage.onAuthenticateSuccess(user)
                        }

                    })
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        registerPage = context as AuthPageListener
    }


    private fun setInputTextEnabled(state: Boolean) {

        inputEmail.isEnabled = state
        inputPass.isEnabled = state

        if (state) {
            loading.visibility = View.GONE
        } else {
            loading.visibility = View.VISIBLE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        AuthRequest.removeSignInListener()
    }
}
