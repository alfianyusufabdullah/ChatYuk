package com.alfianyusufabdullah.chatyuk.data.repository.authentication

import com.alfianyusufabdullah.chatyuk.data.entity.User
import com.alfianyusufabdullah.chatyuk.common.Constant
import com.alfianyusufabdullah.chatyuk.data.route.ChatReferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AuthenticationRepository(private val authentication: FirebaseAuth, private val chatReferences: ChatReferences) {

    fun doRegister(user: User, listener: AuthenticationRepositoryCallback) {

        authentication
                .createUserWithEmailAndPassword(user.email as String, user.confirmPassword as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        val newUser = user.copy(
                                userId = userId,
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
                .signInWithEmailAndPassword(user.email as String, user.password as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        chatReferences.userReferences()
                                .child(userId ?: "0")
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        val newUser = p0.getValue(User::class.java) as User
                                        listener.onSuccess(newUser)
                                    }
                                })

                    } else {
                        listener.onFailed(it.exception?.message)
                    }
                }
    }
}