package com.alfianyusufabdullah.chatyuk.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.presentation.authentication.AuthenticationActivity
import com.alfianyusufabdullah.chatyuk.presentation.chatroom.ChatRoomActivity
import com.alfianyusufabdullah.chatyuk.startActivity

import com.google.firebase.auth.FirebaseAuth

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity<ChatRoomActivity>()
        } else {
            startActivity<AuthenticationActivity>()
        }

        finish()
    }
}