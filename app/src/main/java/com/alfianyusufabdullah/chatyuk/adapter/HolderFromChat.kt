package com.alfianyusufabdullah.chatyuk.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.from_chat.*

class HolderFromChat(override val containerView: View?) : RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    fun bindChatContent(chat: ModelChat) {
        if (chat.isSameUser == true) {
            fromUsernameGroup.visibility = View.GONE
        } else {
            fromUsernameGroup.visibility = View.VISIBLE
        }

        fromUsername.text = chat.user
        fromMessage.text = chat.message
    }
}