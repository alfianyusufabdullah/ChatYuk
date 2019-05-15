package com.alfianyusufabdullah.chatyuk

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
    }

    companion object {
        private lateinit var database: FirebaseDatabase

        fun hideSoftInput(context: Context, editText: EditText) {
            val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }

        fun getFirebaseDatabaseReferences(Reference: String): DatabaseReference {
            return database.getReference(Reference)
        }
    }

}
