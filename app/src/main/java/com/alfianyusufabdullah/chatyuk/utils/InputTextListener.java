package com.alfianyusufabdullah.chatyuk.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 *
 */

public class InputTextListener implements TextWatcher {

    private TextInputLayout input;

    public InputTextListener(TextInputLayout input) {
        this.input = input;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        input.setError("");
        input.setErrorEnabled(false);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
