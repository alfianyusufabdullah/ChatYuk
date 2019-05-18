package com.alfianyusufabdullah.chatyuk.data.repository.authentication

import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.common.Constant
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository(private val authentication: FirebaseAuth, private val chatReferences: ChatReferences) {

    fun doRegister(user: User, listener: AuthenticationRepositoryCallback) {

        authentication
                .createUserWithEmailAndPassword(user.email as String, user.confirmPassword as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        val newUser = user.copy(
                                userId = userId,
                                loginTime = Constant.time,
                                password = null,
                                confirmPassword = null
                        )

                        chatReferences.userReferences()
                                .child(userId ?: "0")
                                .setValue(newUser)

                        listener.onSuccess(newUser)
                    } else {
                        listener.onFailed(it.exception?.message)
                    }
                }
    }

    fun doLogin(user: User, listener: AuthenticationRepositoryCallback) {

        authentication
                .signInWithEmailAndPassword(user.email as String, user.confirmPassword as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        val newUser = user.copy(
                                userId = userId,
                                loginTime = Constant.time,
                                password = null
                        )

                        chatReferences.userReferences()
                                .child(userId ?: "0")
                                .setValue(newUser)

                        listener.onSuccess(newUser)
                    } else {
                        listener.onFailed(it.exception?.message)
                    }
                }
    }
}