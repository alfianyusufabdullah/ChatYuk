package com.alfianyusufabdullah.chatyuk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfianyusufabdullah.chatyuk.adapter.AdapterChat
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import com.alfianyusufabdullah.chatyuk.network.ChatRequest
import com.alfianyusufabdullah.chatyuk.utils.Constant
import com.alfianyusufabdullah.chatyuk.utils.EditTextListener
import com.alfianyusufabdullah.chatyuk.utils.ChatPreferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_layout.*
import java.util.*

class ActivityMain : AppCompatActivity() {

    private lateinit var adapterChat: AdapterChat
    private var listChat: MutableList<ModelChat> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()

        ChatRequest.getChat(object : ChatRequest.OnChatRequest {
            override fun result(chat: ModelChat) {
                listChat.add(chat)

                if (listChat.size > 100) {
                    listChat.removeAt(0)
                }

                adapterChat.notifyDataSetChanged()
            }
        })

        btnSignOut.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Sign Out")
            builder.setMessage("Apakah kamu ingin keluar?")
            builder.setPositiveButton("YES") { _, _ ->
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                startActivity(Intent(it.context, ActivityLauncher::class.java))
                finish()
            }
            builder.setNegativeButton("NO", null)
            builder.create().show()

            mainDrawer.closeDrawers()
        }

    }

    private fun bindUI() {
        adapterChat = AdapterChat(listChat)

        with(chatItem) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = adapterChat
        }

        val user = ChatPreferences.initPreferences(this).userInfo

        nav_user.text = user.username
        nav_email.text = user.email

        mainToolbar.title = "Chat Yuk!"

        val toggle = ActionBarDrawerToggle(this, mainDrawer, mainToolbar, R.string.app_name, R.string.app_name)
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()

        etMessage.addTextChangedListener(EditTextListener(btnSend))

        btnSend.setOnClickListener {
            val username = user.username
            val message = etMessage?.text.toString()
            val time = Constant.time

            val chat = ModelChat()
            chat.user = username
            chat.message = message
            chat.time = time

            ChatRequest.postMessage(chat)

            etMessage?.setText("")
            MyApplication.hideSoftInput(this@ActivityMain, etMessage)
        }
    }
}
