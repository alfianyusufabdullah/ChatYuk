package com.alfianyusufabdullah.chatyuk

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.replaceFragment(resources: Int, fragment: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(resources, fragment)
            .commit()
}

fun Context.hideSoftInput(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun View.showSnackbar(message: String?) {
    Snackbar.make(rootView, message ?: "empty", Snackbar.LENGTH_SHORT).show()
}

fun String.isEmailValid() = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
