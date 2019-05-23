package com.alfianyusufabdullah.chatyuk.presentation.chatroom.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.data.entity.Chat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.to_message.*

class HolderMessageTo(override val containerView: View?) : RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    fun bindChatContent(chat: Chat, onItemClickListener: AdapterMessage.OnItemClickListener) {
        if (chat.isSameUser == true) {
            toUsernameGroup.visibility = View.GONE
        } else {
            toUsernameGroup.visibility = View.VISIBLE
        }

        toUsername.text = chat.user

        if (chat.isDeleted == true) {
            toMessage.setTypeface(null, Typeface.ITALIC)
            toMessage.setTextColor(Color.GRAY)
            toMessage.text = itemView.context.getString(R.string.message_delete)
        } else {
            toMessage.text = chat.message
            toMessage.setTypeface(null, Typeface.NORMAL)
            toMessage.setTextColor(Color.BLACK)
        }

        val popup = PopupMenu(itemView.context, toMessage)
        popup.menu.add("Hapus Pesan")
        popup.setOnMenuItemClickListener {
            if (it.title == "Hapus Pesan") {
                onItemClickListener.deleteMessage(chat)
            }

            false
        }

        toMessage.setOnLongClickListener {
            popup.show()
            false
        }
    }
}