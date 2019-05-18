package com.alfianyusufabdullah.chatyuk.presentation.chatroom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfianyusufabdullah.chatyuk.presentation.LauncherActivity
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.presentation.chatroom.adapter.AdapterMessage
import com.alfianyusufabdullah.chatyuk.data.entity.Chat
import com.alfianyusufabdullah.chatyuk.preferences.ChatPreferences
import com.alfianyusufabdullah.chatyuk.common.Constant
import com.alfianyusufabdullah.chatyuk.common.EditTextListener
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepository
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_chat_room.*
import java.util.*

class ChatRoomActivity : AppCompatActivity(), ChatRoomView {

    private lateinit var adapterMessage: AdapterMessage
    private lateinit var chatRoomPresenter: ChatRoomPresenter
    private var listChat: MutableList<Chat> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val user = ChatPreferences.initPreferences(this).userInfo
        mainToolbar.title = "Chat Yuk!"

        setSupportActionBar(mainToolbar)
        etMessage.addTextChangedListener(EditTextListener(btnSend))

        adapterMessage = AdapterMessage(this, listChat)

        with(chat) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            adapter = adapterMessage
        }

        btnSend.setOnClickListener {
            val username = user.username
            val message = etMessage?.text.toString()
            val time = Constant.time

            val chat = Chat()
            chat.user = username
            chat.message = message
            chat.time = time

            chatRoomPresenter.sendMessage(chat)

            etMessage?.setText("")
        }

        chatRoomPresenter = ChatRoomPresenter(MessageRepository(ChatReferences()))
        chatRoomPresenter.attachView(this)
        chatRoomPresenter.getMessages()
    }

    override fun onMessageComing(chat: Chat) {
        listChat.add(chat)
        adapterMessage.notifyDataSetChanged()
    }

    override fun onMessageUpdate(position: Int, chat: Chat) {
        listChat[position] = chat
        adapterMessage.notifyItemChanged(position, chat)
    }

    override fun onMessageDeleted(position: Int) {
        listChat.removeAt(position)
        adapterMessage.notifyItemRemoved(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        chatRoomPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.menu_logout) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Sign Out")
            builder.setMessage("Apakah kamu ingin keluar?")
            builder.setPositiveButton("YES") { _, _ ->
                val auth = FirebaseAuth.getInstance()
                auth.signOut()

                startActivity(Intent(this, LauncherActivity::class.java))
                finish()
            }
            builder.setNegativeButton("NO", null)
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}