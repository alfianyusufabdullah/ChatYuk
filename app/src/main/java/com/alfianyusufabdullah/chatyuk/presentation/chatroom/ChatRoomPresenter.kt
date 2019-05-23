package com.alfianyusufabdullah.chatyuk.presentation.chatroom

import com.alfianyusufabdullah.chatyuk.data.entity.Chat
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepository
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepositoryCallback

class ChatRoomPresenter(private val messageRepository: MessageRepository) {

    private var view: ChatRoomView? = null
    private var chatPosition = 0
    private val messages = mutableListOf<Chat>()

    fun attachView(view: ChatRoomView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun getMessages() {
        messageRepository.getMessage(object : MessageRepositoryCallback {
            override fun onMessageComing(chat: Chat) {
                if (chatPosition == 0) {
                    view?.onMessageComing(chat.copy(
                            isSameUser = false
                    ))
                } else {
                    val before = messages[chatPosition - 1].user
                    view?.onMessageComing(chat.copy(
                            isSameUser = before == chat.user
                    ))
                }

                messages.add(chat)
                chatPosition++
            }

            override fun onMessageUpdate(position: Int, chat: Chat) {
                if (position == 0) {
                    view?.onMessageUpdate(position ,chat.copy(
                            isSameUser = false
                    ))
                } else {
                    val before = messages[chatPosition - 1].user
                    view?.onMessageUpdate(position ,chat.copy(
                            isSameUser = before == chat.user
                    ))
                }
            }

            override fun onMessageDeleted(position: Int) {
                view?.onMessageDeleted(position)

                chatPosition--
            }
        })
    }

    fun deleteMessage(chat: Chat){
        messageRepository.deleteMessage(chat)
    }

    fun sendMessage(chat: Chat) {
        messageRepository.sendMessage(chat)
    }
}