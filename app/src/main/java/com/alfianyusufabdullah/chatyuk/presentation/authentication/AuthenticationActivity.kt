package com.alfianyusufabdullah.chatyuk.presentation.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.preferences.ChatPreferences
import com.alfianyusufabdullah.chatyuk.presentation.authentication.login.LoginFragment
import com.alfianyusufabdullah.chatyuk.presentation.authentication.register.RegisterFragment
import com.alfianyusufabdullah.chatyuk.presentation.chatroom.ChatRoomActivity
import com.alfianyusufabdullah.chatyuk.replaceFragment
import com.alfianyusufabdullah.chatyuk.startActivity

class AuthenticationActivity : AppCompatActivity(), AuthenticationPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            replaceFragment(R.id.containerAuth, LoginFragment())
        }
    }

    override fun onLoginPage() = replaceFragment(R.id.containerAuth, LoginFragment())

    override fun onRegisterPage() = replaceFragment(R.id.containerAuth, RegisterFragment())

    override fun onAuthenticateSuccess(user: User) {
        ChatPreferences.initPreferences(this).userInfo = user
        startActivity<ChatRoomActivity>()
        finish()
    }
}