package com.alfianyusufabdullah.chatyuk.common

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

    val time: String
        get() = SimpleDateFormat("dd MMM yyyy , HH.mm", Locale.getDefault()).format(Date())
}
