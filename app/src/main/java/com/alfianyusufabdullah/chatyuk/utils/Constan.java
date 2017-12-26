package com.alfianyusufabdullah.chatyuk.utils;

import android.annotation.SuppressLint;
import android.util.Patterns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 *
 */

public class Constan {

    public static final int CONS_USERNAME = 0;
    public static final int CONS_EMAIL = 1;
    public static final int CONS_PASS = 2;
    public static final int CONS_CONFIRM_PASS = 3;

    public static final String PREF_USERNAME = "user";
    public static final String PREF_USERID = "id";
    public static final String PREF_EMAIL = "email";
    public static final String PREF_LOGIN_TIME = "login_time";

    public static final String ERR_EMAIL_NOT_EXISTS = "There is no user record corresponding to this identifier. The user may have been deleted.";
    public static final String ERR_EMAIL_EXISTS = "The email address is already in use by another account.";
    public static final String ERR_PASS = "The password is invalid or the user does not have a password.";

    public static boolean isEmailValid (String Email){
        return !Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime() {
        return new SimpleDateFormat("dd MMM yyyy , HH.mm").format(new Date());
    }
}
