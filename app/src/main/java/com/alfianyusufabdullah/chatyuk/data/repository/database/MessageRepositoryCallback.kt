package com.alfianyusufabdullah.chatyuk.data.repository.database

import com.alfianyusufabdullah.chatyuk.data.entity.Chat

interface MessageRepositoryCallback {
    fun onMessageComing(chat: Chat)
    fun onMessageUpdate(position: Int, chat: Chat)
    fun onMessageDeleted(position: Int)
}