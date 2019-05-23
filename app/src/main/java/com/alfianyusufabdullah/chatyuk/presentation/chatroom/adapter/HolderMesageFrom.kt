package com.alfianyusufabdullah.chatyuk.presentation.chatroom.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.data.entity.Chat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.from_message.*

class HolderMesageFrom(override val containerView: View?) : RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    fun bindChatContent(chat: Chat, onItemClickListener: AdapterMessage.OnItemClickListener) {
        if (chat.isSameUser == true) {
            fromUsernameGroup.visibility = View.GONE
        } else {
            fromUsernameGroup.visibility = View.VISIBLE
        }

        fromUsername.text = chat.user

        if (chat.isDeleted == true) {
            fromMessage.setTypeface(null, Typeface.ITALIC)
            fromMessage.setTextColor(Color.GRAY)
            fromMessage.text = itemView.context.getString(R.string.message_delete)
        } else {
            fromMessage.setTypeface(null, Typeface.NORMAL)
            fromMessage.setTextColor(Color.BLACK)
            fromMessage.text = chat.message
        }

        itemView.setOnClickListener { onItemClickListener.deleteMessage(chat) }
    }
}