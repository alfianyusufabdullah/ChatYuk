package com.alfianyusufabdullah.chatyuk.presentation.authentication

import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfianyusufabdullah.chatyuk.presentation.chatroom.ChatRoomActivity
import com.alfianyusufabdullah.chatyuk.R

import com.alfianyusufabdullah.chatyuk.presentation.authentication.login.LoginFragment
import com.alfianyusufabdullah.chatyuk.presentation.authentication.register.RegisterFragment
import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.preferences.ChatPreferences

class AuthenticationActivity : AppCompatActivity(), AuthenticationPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerAuth, LoginFragment())
                    .commit()
        }
    }

    override fun onLoginPage() {
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth, LoginFragment())
                .commit()
    }

    override fun onRegisterPage() {
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth, RegisterFragment())
                .commit()
    }

    override fun onAuthenticateSuccess(user: User) {
        ChatPreferences.initPreferences(this).userInfo = user
        startActivity(Intent(this@AuthenticationActivity, ChatRoomActivity::class.java))
        finish()
    }
}
