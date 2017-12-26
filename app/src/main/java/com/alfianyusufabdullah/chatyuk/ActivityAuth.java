package com.alfianyusufabdullah.chatyuk;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alfianyusufabdullah.chatyuk.fragment.FragmentLogin;
import com.alfianyusufabdullah.chatyuk.fragment.FragmentRegister;
import com.alfianyusufabdullah.chatyuk.interfaces.onAuthPageListener;

public class ActivityAuth extends AppCompatActivity implements onAuthPageListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerAuth , new FragmentLogin())
                    .commit();
        }
    }

    @Override
    public void onLoginPage() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth , new FragmentLogin())
                .commit();
    }

    @Override
    public void onRegisterPage() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth , new FragmentRegister())
                .commit();
    }

    @Override
    public void onAuthenticateSuccess() {
        startActivity(new Intent(ActivityAuth.this , ActivityMain.class));
        finish();
    }
}
