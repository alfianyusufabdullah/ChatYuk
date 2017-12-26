package com.alfianyusufabdullah.chatyuk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 *
 */

public class MyApplication extends Application {

    private static MyApplication application;
    private static FirebaseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

    }

    public static MyApplication getInstance(){
        return application;
    }

    public static MyApplication hideSoftInput(Context context , EditText editText){
        InputMethodManager im = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(editText.getWindowToken() , InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        return application;
    }

    public SharedPreferences getSharedPreferences(){
        return getSharedPreferences("user" , MODE_PRIVATE);
    }

    public static DatabaseReference getFirebaseDatabaseReferences(String Reference){
        return database.getReference(Reference);
    }

}
