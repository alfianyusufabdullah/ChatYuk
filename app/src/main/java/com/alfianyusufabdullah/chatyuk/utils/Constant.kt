package com.alfianyusufabdullah.chatyuk.utils

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

object Constant {

    const val PREF_USERNAME = "user"
    const val PREF_USER_ID = "id"
    const val PREF_EMAIL = "email"
    const val PREF_LOGIN_TIME = "login_time"

    const val ERR_EMAIL_NOT_EXISTS = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val ERR_EMAIL_EXISTS = "The email address is already in use by another account."
    const val ERR_PASS = "The password is invalid or the user does not have a password."

    val time: String
        get() = SimpleDateFormat("dd MMM yyyy , HH.mm", Locale.getDefault()).format(Date())

    fun isEmailValid(Email: String): Boolean {
        return Email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches()
    }
}
