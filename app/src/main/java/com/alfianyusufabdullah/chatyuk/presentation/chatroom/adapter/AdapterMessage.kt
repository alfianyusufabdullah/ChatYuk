package com.alfianyusufabdullah.chatyuk.presentation.chatroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.data.entity.Chat
import com.alfianyusufabdullah.chatyuk.preferences.ChatPreferences

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class AdapterMessage(context: Context, private val listChat: List<Chat>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FROM = 1
        const val TO = 2
    }

    val user = ChatPreferences.initPreferences(context).userInfo.username

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FROM -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.from_message, parent, false)
                HolderMesageFrom(v)
            }

            TO -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.to_message, parent, false)
                HolderMessageTo(v)
            }

            else -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.from_message, parent, false)
                HolderMesageFrom(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderMesageFrom -> holder.bindChatContent(listChat[position], onItemClickListener)
            is HolderMessageTo -> holder.bindChatContent(listChat[position], onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position].user == user) TO else FROM
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    interface OnItemClickListener {
        fun deleteMessage(chat: Chat)
    }
}