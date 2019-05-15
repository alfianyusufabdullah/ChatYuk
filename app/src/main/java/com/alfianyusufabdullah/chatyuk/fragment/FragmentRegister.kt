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
import com.alfianyusufabdullah.chatyuk.network.AuthRequest
import com.alfianyusufabdullah.chatyuk.utils.Constant
import com.alfianyusufabdullah.chatyuk.utils.InputTextListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*

class FragmentRegister : Fragment() {

    private lateinit var loginpage: AuthPageListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regisUser.addTextChangedListener(InputTextListener(inputUsername))
        regisEmail.addTextChangedListener(InputTextListener(inputEmail))
        regisPass.addTextChangedListener(InputTextListener(inputPass))
        regisPassConfirm.addTextChangedListener(InputTextListener(inputPassConfirm))

        txtSignIn.setOnClickListener { loginpage.onLoginPage() }

        btnSignUp.setOnClickListener {
            val username = regisUser.text.toString().trim()
            val email = regisEmail.text.toString().trim()
            val password = regisPass.text.toString().trim()
            val confirmPassword = regisPassConfirm.text.toString().trim()

            if (username.isEmpty()) {
                inputUsername.error = "Masukkan Username"
                regisUser.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                inputEmail.error = "Masukkan Email"
                regisEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Constant.isEmailValid(email)) {
                inputEmail.error = "Invalid Email"
                regisEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                inputPass.error = "Masukkan Password"
                regisPass.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                inputPass.error = "Password Terlalu Pendek"
                regisPass.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                inputPassConfirm.error = "Masukkan Konfirmasi Password"
                regisPassConfirm.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword != password) {
                inputPassConfirm.error = "Password Salah"
                regisPassConfirm.requestFocus()
                return@setOnClickListener
            }

            MyApplication.hideSoftInput(context as ActivityAuth, regisUser)

            it.isEnabled = false
            setInputTextEnabled(false)

            AuthRequest.field(username, email, confirmPassword)
                    .doRegister(object : AuthRequestListener {
                        override fun onAuthFailed(error: String) {

                            it.isEnabled = true
                            setInputTextEnabled(true)

                            if (Constant.ERR_EMAIL_EXISTS == error) {
                                inputEmail.error = "Email yang kamu masukkan sudah terdaftar"
                                regisEmail.requestFocus()
                            } else {
                                showSnackbar(error)
                            }
                        }

                        override fun onAuthSuccess() {
                            loginpage.onAuthenticateSuccess()
                        }
                    })
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loginpage = context as AuthPageListener
    }

    private fun setInputTextEnabled(state: Boolean) {
        inputUsername.isEnabled = state
        inputEmail.isEnabled = state
        inputPass.isEnabled = state
        inputPassConfirm.isEnabled = state

        if (state) {
            loading.visibility = View.GONE
        } else {
            loading.visibility = View.VISIBLE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

}
