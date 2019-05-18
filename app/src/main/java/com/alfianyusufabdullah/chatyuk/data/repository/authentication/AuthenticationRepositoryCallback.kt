package com.alfianyusufabdullah.chatyuk.data.repository.authentication

import com.alfianyusufabdullah.chatyuk.data.entity.User

interface AuthenticationRepositoryCallback {
    fun onSuccess(user: User)
    fun onFailed(error: String?)
}