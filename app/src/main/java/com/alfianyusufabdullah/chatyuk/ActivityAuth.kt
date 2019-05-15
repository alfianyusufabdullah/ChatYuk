package com.alfianyusufabdullah.chatyuk

import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.alfianyusufabdullah.chatyuk.fragment.FragmentLogin
import com.alfianyusufabdullah.chatyuk.fragment.FragmentRegister
import com.alfianyusufabdullah.chatyuk.interfaces.AuthPageListener

class ActivityAuth : AppCompatActivity(), AuthPageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerAuth, FragmentLogin())
                    .commit()
        }
    }

    override fun onLoginPage() {
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth, FragmentLogin())
                .commit()
    }

    override fun onRegisterPage() {
        supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth, FragmentRegister())
                .commit()
    }

    override fun onAuthenticateSuccess() {
        startActivity(Intent(this@ActivityAuth, ActivityMain::class.java))
        finish()
    }
}
