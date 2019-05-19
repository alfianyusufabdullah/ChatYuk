package com.alfianyusufabdullah.chatyuk

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.alfianyusufabdullah.chatyuk.module.chatModule
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.core.context.startKoin

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)

        startKoin { modules(chatModule) }
    }
}