package com.alfianyusufabdullah.chatyuk.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.alfianyusufabdullah.chatyuk.interfaces.onAuthRequestListener;
import com.alfianyusufabdullah.chatyuk.model.ModelUser;
import com.alfianyusufabdullah.chatyuk.MyApplication;
import com.alfianyusufabdullah.chatyuk.utils.Constan;
import com.alfianyusufabdullah.chatyuk.utils.PreferencesManager;

public class AuthRequest {

    private static String Username;
    private static String Email;
    private static String Password;

    private static DatabaseReference databaseReference;
    private static ValueEventListener eventListener;

    public static AuthRequest Field(String username, String email, String password) {
        Username = username;
        Email = email;
        Password = password;
        return new AuthRequest();
    }

    public static AuthRequest Field(String email, String password) {
        Email = email;
        Password = password;
        return new AuthRequest();
    }

    public void startRegister(final onAuthRequestListener listener) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String UserID = task.getResult().getUser().getUid();

                            ModelUser user = new ModelUser();
                            user.setUsername(Username);
                            user.setEmail(Email);
                            user.setUserId(UserID);
                            user.setLoginTime(Constan.getTime());

                            PreferencesManager.initPreferences().setUserInfo(user);
                            MyApplication.getFirebaseDatabaseReferences("user").child(UserID).setValue(user);

                            listener.onAuthSuccess();
                        } else {
                            listener.onAuthFailed(task.getException().getMessage());
                            Log.d("RegisterRequest", "onComplete: " + task.getException().getMessage());
                        }

                    }
                });

    }

    public void startLogin(final onAuthRequestListener listener) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            final String UserID = task.getResult().getUser().getUid();

                            databaseReference = MyApplication.getFirebaseDatabaseReferences("user").child(UserID);
                            databaseReference.addListenerForSingleValueEvent(eventListener(listener, UserID));

                        } else {
                            listener.onAuthFailed(task.getException().getMessage());
                        }

                    }
                });
    }

    private ValueEventListener eventListener(final onAuthRequestListener listener, final String UserID) {
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ModelUser user = dataSnapshot.getValue(ModelUser.class);

                if (user != null) {
                    user.setLoginTime(Constan.getTime());
                    MyApplication.getFirebaseDatabaseReferences("user").child(UserID).setValue(user);
                    PreferencesManager.initPreferences().setUserInfo(user);

                    listener.onAuthSuccess();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return eventListener;
    }

    public static void removeSignInListener() {
        if (eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
    }
}
