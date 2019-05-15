package com.alfianyusufabdullah.chatyuk.network

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

import com.alfianyusufabdullah.chatyuk.interfaces.AuthRequestListener
import com.alfianyusufabdullah.chatyuk.model.ModelUser
import com.alfianyusufabdullah.chatyuk.MyApplication
import com.alfianyusufabdullah.chatyuk.utils.Constant
import com.google.firebase.auth.FirebaseAuth

class AuthRequest {

    companion object {

        private lateinit var databaseReference: DatabaseReference
        private lateinit var valueEventListener: ValueEventListener

        private lateinit var username: String
        private lateinit var email: String
        private lateinit var password: String

        fun field(email: String, password: String, username: String = "username"): AuthRequest {
            this.username = username
            this.email = email
            this.password = password

            return AuthRequest()
        }

        fun removeSignInListener() {
            if (this::valueEventListener.isInitialized) databaseReference.removeEventListener(valueEventListener)
        }

        private fun evenListener(listener: AuthRequestListener, userId: String): ValueEventListener {
            valueEventListener = object : ValueEventListener {
                override fun onCancelled(database: DatabaseError) {
                    listener.onAuthFailed(database.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    with(snapshot.value as? ModelUser) {
                        this?.let {
                            it.loginTime = Constant.time
                            MyApplication.getFirebaseDatabaseReferences("user").child(userId).setValue(it)
                            listener.onAuthSuccess(it)
                        }
                    }
                }
            }

            return valueEventListener
        }
    }

    fun doRegister(listener: AuthRequestListener) {
        Log.d("EMAIL", email )
        Log.d("PASSWORD", password)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        val user = ModelUser()
                        user.username = username
                        user.email = email
                        user.userId = userId
                        user.loginTime = Constant.time

                        MyApplication.getFirebaseDatabaseReferences("user")
                                .child(userId ?: "0")
                                .setValue(user)

                        listener.onAuthSuccess(user)
                    } else {
                        listener.onAuthFailed(it.exception?.localizedMessage ?: "error")
                        Log.d("RegisterRequest", "onFailed: " + it.exception?.localizedMessage)
                    }
                }
    }

    fun doLogin(listener: AuthRequestListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid

                        databaseReference = MyApplication.getFirebaseDatabaseReferences("user")
                                .child(userId ?: "0")
                        databaseReference.addListenerForSingleValueEvent(
                                evenListener(listener, userId ?: "0")
                        )
                    }
                }
    }
}
