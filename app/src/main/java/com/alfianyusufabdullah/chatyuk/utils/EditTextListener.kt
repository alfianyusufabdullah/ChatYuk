package com.alfianyusufabdullah.chatyuk.utils

import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class EditTextListener(private val btn: Button) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        TransitionManager.beginDelayedTransition(btn.rootView as ViewGroup)
        if (charSequence.isEmpty()) {
            btn.visibility = View.GONE
        } else {
            btn.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(editable: Editable) {

    }
}
