package com.alfianyusufabdullah.chatyuk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.R
import com.alfianyusufabdullah.chatyuk.model.ModelChat

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class AdapterChat(private val dataChat: List<ModelChat>) : RecyclerView.Adapter<HolderChat>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderChat {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false)
        return HolderChat(v)
    }

    override fun onBindViewHolder(holder: HolderChat, position: Int) {
        holder.setContent(dataChat[position])
    }

    override fun getItemCount(): Int {
        return dataChat.size
    }
}
