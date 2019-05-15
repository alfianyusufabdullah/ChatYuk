package com.alfianyusufabdullah.chatyuk.network

import com.alfianyusufabdullah.chatyuk.MyApplication
import com.alfianyusufabdullah.chatyuk.model.ModelChat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

object ChatRequest {

    fun getChat(chatResult: OnChatRequest) {

        val reference = MyApplication.getFirebaseDatabaseReferences("chat")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val chat = dataSnapshot.getValue(ModelChat::class.java)
                chatResult.result(chat as ModelChat)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    fun postMessage(chat: ModelChat) {

        val reference = MyApplication.getFirebaseDatabaseReferences("chat")
        val keyChat = reference.push().key
        reference.child(keyChat ?: "0").setValue(chat)

    }

    interface OnChatRequest {
        fun result(chat: ModelChat)
    }
}