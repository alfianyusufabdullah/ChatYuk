package com.alfianyusufabdullah.chatyuk.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import com.alfianyusufabdullah.chatyuk.utils.ChatPreferences
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_chat.*

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class HolderChat(override val containerView: View) : RecyclerView.ViewHolder(containerView) ,LayoutContainer {

    fun setContent(chat: ModelChat) {

        val from = chat.user
        val user = ChatPreferences.initPreferences(itemView.context).userInfo.username

        if (from == user) {
            cardTo.visibility = View.VISIBLE
            cardFrom.visibility = View.GONE

            toUsername.text = chat.user
            toMessage.text = chat.message
            toTime.text = chat.time

        } else {
            cardTo.visibility = View.GONE
            cardFrom.visibility = View.VISIBLE

            fromUsername.text = chat.user
            fromMessage.text = chat.message
            fromTime.text = chat.time
        }
    }
}