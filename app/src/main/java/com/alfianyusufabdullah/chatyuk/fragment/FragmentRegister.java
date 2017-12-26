package com.alfianyusufabdullah.chatyuk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alfianyusufabdullah.chatyuk.R;
import com.alfianyusufabdullah.chatyuk.interfaces.onAuthRequestListener;
import com.alfianyusufabdullah.chatyuk.MyApplication;
import com.alfianyusufabdullah.chatyuk.network.AuthRequest;
import com.alfianyusufabdullah.chatyuk.interfaces.onAuthPageListener;
import com.alfianyusufabdullah.chatyuk.utils.Constan;
import com.alfianyusufabdullah.chatyuk.utils.InputTextListener;

public class FragmentRegister extends Fragment {

    onAuthPageListener loginpage;
    @BindViews({R.id.inputUsername, R.id.inputEmail, R.id.inputPass, R.id.inputPassConfirm})
    List<TextInputLayout> inputText;

    @BindViews({R.id.regisUser, R.id.regisEmail, R.id.regisPass, R.id.regisPassConfirm})
    List<EditText> regisEditText;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.rootView)
    View rootView;

    public FragmentRegister() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < inputText.size(); i++) {
            regisEditText.get(i).addTextChangedListener(new InputTextListener(inputText.get(i)));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginpage = (onAuthPageListener) context;
    }

    @OnClick(R.id.txtSignIn)
    public void loginPage(View view) {
        loginpage.onLoginPage();
    }

    @OnClick(R.id.btnSignUp)
    public void signUp(final View view) {

        String Username = regisEditText.get(Constan.CONS_USERNAME).getText().toString();
        String Email = regisEditText.get(Constan.CONS_EMAIL).getText().toString();
        String Pass = regisEditText.get(Constan.CONS_PASS).getText().toString();
        String ConfirmPass = regisEditText.get(Constan.CONS_CONFIRM_PASS).getText().toString();

        if (Username.isEmpty()) {
            inputText.get(Constan.CONS_USERNAME).setError("Masukkan Username");
            regisEditText.get(Constan.CONS_USERNAME).requestFocus();
            return;
        }

        if (Email.isEmpty()) {
            inputText.get(Constan.CONS_EMAIL).setError("Masukkan Email");
            regisEditText.get(Constan.CONS_EMAIL).requestFocus();
            return;
        }

        if (!Constan.isEmailValid(Email)) {
            inputText.get(Constan.CONS_EMAIL).setError("Invalid Email");
            regisEditText.get(Constan.CONS_EMAIL).requestFocus();
            return;
        }

        if (Pass.isEmpty()) {
            inputText.get(Constan.CONS_PASS).setError("Masukkan Password");
            regisEditText.get(Constan.CONS_PASS).requestFocus();
            return;
        }

        if (Pass.length() < 6) {
            inputText.get(Constan.CONS_PASS).setError("Password Terlalu Pendek");
            regisEditText.get(Constan.CONS_PASS).requestFocus();
            return;
        }

        if (ConfirmPass.isEmpty()) {
            inputText.get(Constan.CONS_CONFIRM_PASS).setError("Masukkan Konfirmasi Password");
            regisEditText.get(Constan.CONS_CONFIRM_PASS).requestFocus();
            return;
        }

        if (!ConfirmPass.equals(Pass)) {
            inputText.get(Constan.CONS_CONFIRM_PASS).setError("Password Salah");
            regisEditText.get(Constan.CONS_CONFIRM_PASS).requestFocus();
            return;
        }

        MyApplication.hideSoftInput(getActivity(), regisEditText.get(0));

        view.setEnabled(false);
        setInputTextEnabled(false);

        AuthRequest.Field(Username, Email, ConfirmPass)
                .startRegister(new onAuthRequestListener() {
                    @Override
                    public void onAuthFailed(String Error) {

                        view.setEnabled(true);
                        setInputTextEnabled(true);

                        switch (Error) {
                            case Constan.ERR_EMAIL_EXISTS:
                                inputText.get(Constan.CONS_EMAIL).setError("Email Yang Kamu Masukkan Sudah Terdaftar");
                                regisEditText.get(Constan.CONS_EMAIL).requestFocus();
                                break;
                            default:
                                showSnacbar(Error);
                                break;
                        }
                    }

                    @Override
                    public void onAuthSuccess() {
                        loginpage.onAuthenticateSuccess();
                    }
                });

    }

    private void setInputTextEnabled(boolean state) {
        for (int i = 0; i < inputText.size(); i++) {
            inputText.get(i).setEnabled(state);
        }

        if (state) {
            loading.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.VISIBLE);
        }
    }

    private void showSnacbar(String Pesan) {
        Snackbar.make(rootView, Pesan, Snackbar.LENGTH_SHORT).show();
    }

}
