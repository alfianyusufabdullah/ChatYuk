package com.alfianyusufabdullah.chatyuk.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.to_chat.*

class HolderToChat(override val containerView: View?) : RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    fun bindChatContent(chat: ModelChat) {
        if (chat.isSameUser == true) {
            toUsernameGroup.visibility = View.GONE
        } else {
            toUsernameGroup.visibility = View.VISIBLE
        }

        toUsername.text = chat.user
        toMessage.text = chat.message
    }
}