package com.alfianyusufabdullah.chatyuk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import com.alfianyusufabdullah.chatyuk.utils.ChatPreferences

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class AdapterChat(context: Context, private val dataChat: List<ModelChat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FROM = 1
        const val TO = 2
    }

    val user = ChatPreferences.initPreferences(context).userInfo.username

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FROM -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.from_chat, parent, false)
                HolderFromChat(v)
            }

            TO -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.to_chat, parent, false)
                HolderToChat(v)
            }

            else -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.from_chat, parent, false)
                HolderFromChat(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderFromChat -> holder.bindChatContent(dataChat[position])
            is HolderToChat -> holder.bindChatContent(dataChat[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataChat[position].user == user) TO else FROM
    }

    override fun getItemCount(): Int {
        return dataChat.size
    }
}
