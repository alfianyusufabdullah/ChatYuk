package com.alfianyusufabdullah.chatyuk

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfianyusufabdullah.chatyuk.adapter.AdapterChat
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import com.alfianyusufabdullah.chatyuk.network.ChatRequest
import com.alfianyusufabdullah.chatyuk.utils.ChatPreferences
import com.alfianyusufabdullah.chatyuk.utils.Constant
import com.alfianyusufabdullah.chatyuk.utils.EditTextListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ActivityMain : AppCompatActivity() {

    private lateinit var adapterChat: AdapterChat
    private var chatPosition: Int = 0
    private var listChat: MutableList<ModelChat> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()

        ChatRequest.getChat(object : ChatRequest.OnChatRequest {
            override fun result(chat: ModelChat) {

                if (chatPosition == 0) {
                    chat.isSameUser = false
                } else {
                    val before = listChat[chatPosition - 1].user
                    chat.isSameUser = before == chat.user
                }

                listChat.add(chat)

                if (listChat.size > 100) {
                    listChat.removeAt(0)
                }

                adapterChat.notifyDataSetChanged()

                chatPosition++
            }
        })

    }

    private fun bindUI() {
        adapterChat = AdapterChat(this, listChat)

        with(chatItem) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            adapter = adapterChat
        }

        val user = ChatPreferences.initPreferences(this).userInfo
        mainToolbar.title = "Chat Yuk!"

        setSupportActionBar(mainToolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.menu_logout) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Sign Out")
            builder.setMessage("Apakah kamu ingin keluar?")
            builder.setPositiveButton("YES") { _, _ ->
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                startActivity(Intent(this, ActivityLauncher::class.java))
                finish()
            }
            builder.setNegativeButton("NO", null)
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}
