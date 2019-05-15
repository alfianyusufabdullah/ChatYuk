package com.alfianyusufabdullah.chatyuk.utils

import com.google.android.material.textfield.TextInputLayout
import android.text.Editable
import android.text.TextWatcher

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

class InputTextListener(private val input: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        input.error = ""
        input.isErrorEnabled = false
    }

    override fun afterTextChanged(editable: Editable) {

    }
}
