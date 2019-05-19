package com.alfianyusufabdullah.chatyuk.module

import com.alfianyusufabdullah.chatyuk.data.repository.authentication.AuthenticationRepository
import com.alfianyusufabdullah.chatyuk.data.repository.database.MessageRepository
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.alfianyusufabdullah.chatyuk.presentation.authentication.login.LoginPresenter
import com.alfianyusufabdullah.chatyuk.presentation.authentication.register.RegisterPresenter
import com.alfianyusufabdullah.chatyuk.presentation.chatroom.ChatRoomPresenter
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val chatModule = module {
    single { ChatReferences() }
    single { FirebaseAuth.getInstance() }

    factory { AuthenticationRepository(get(), get()) }
    factory { MessageRepository(get()) }

    factory { LoginPresenter(get()) }
    factory { RegisterPresenter(get()) }
    factory { ChatRoomPresenter(get()) }
}