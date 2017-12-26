package com.alfianyusufabdullah.chatyuk.utils;

import android.content.SharedPreferences;

import com.alfianyusufabdullah.chatyuk.model.ModelUser;
import com.alfianyusufabdullah.chatyuk.MyApplication;

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

public class PreferencesManager {

    private static SharedPreferences preferences;

    public static PreferencesManager initPreferences() {
        preferences = MyApplication.getInstance().getSharedPreferences();
        return new PreferencesManager();
    }

    public void setUserInfo(ModelUser modelUser) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constan.PREF_USERNAME, modelUser.getUsername());
        editor.putString(Constan.PREF_EMAIL, modelUser.getEmail());
        editor.putString(Constan.PREF_USERID, modelUser.getUserId());
        editor.putString(Constan.PREF_LOGIN_TIME, modelUser.getLoginTime());
        editor.apply();
    }

    public ModelUser getUserInfo() {

        ModelUser user = new ModelUser();
        user.setUsername(preferences.getString(Constan.PREF_USERNAME, ""));
        user.setEmail(preferences.getString(Constan.PREF_EMAIL, ""));
        user.setUserId(preferences.getString(Constan.PREF_USERID, ""));
        user.setLoginTime(preferences.getString(Constan.PREF_LOGIN_TIME, ""));

        return user;
    }
}
