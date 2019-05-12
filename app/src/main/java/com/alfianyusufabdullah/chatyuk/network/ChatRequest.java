package com.alfianyusufabdullah.chatyuk.network;

import com.alfianyusufabdullah.chatyuk.MyApplication;
import com.alfianyusufabdullah.chatyuk.model.ModelChat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

public class ChatRequest {

    public static void getChat(final OnChatRequest chatResult) {

        DatabaseReference reference = MyApplication.getFirebaseDatabaseReferences("chat");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ModelChat chat = dataSnapshot.getValue(ModelChat.class);
                chatResult.result(chat);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void postMessage(ModelChat chat){

        DatabaseReference reference = MyApplication.getFirebaseDatabaseReferences("chat");
        String keyChat = reference.push().getKey();
        reference.child(keyChat).setValue(chat);

    }

    public interface OnChatRequest {
        void result(ModelChat chat);
    }
}