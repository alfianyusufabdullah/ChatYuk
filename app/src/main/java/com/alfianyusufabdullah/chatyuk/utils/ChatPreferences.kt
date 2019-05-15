package com.alfianyusufabdullah.chatyuk.utils

import android.content.Context
import android.content.SharedPreferences

import com.alfianyusufabdullah.chatyuk.model.ModelUser

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class ChatPreferences {

    var userInfo: ModelUser
        get() {
            val user = ModelUser()
            user.username = preferences.getString(Constant.PREF_USERNAME, "")
            user.email = preferences.getString(Constant.PREF_EMAIL, "")
            user.userId = preferences.getString(Constant.PREF_USER_ID, "")
            user.loginTime = preferences.getString(Constant.PREF_LOGIN_TIME, "")
            return user
        }
        set(modelUser) {
            val editor = preferences.edit()
            editor.putString(Constant.PREF_USERNAME, modelUser.username)
            editor.putString(Constant.PREF_EMAIL, modelUser.email)
            editor.putString(Constant.PREF_USER_ID, modelUser.userId)
            editor.putString(Constant.PREF_LOGIN_TIME, modelUser.loginTime)
            editor.apply()
        }

    companion object {

        private lateinit var preferences: SharedPreferences

        fun initPreferences(context: Context): ChatPreferences {
            preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            return ChatPreferences()
        }
    }
}
