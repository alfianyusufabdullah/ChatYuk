package com.alfianyusufabdullah.chatyuk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alfianyusufabdullah.chatyuk.adapter.AdapterChat;
import com.alfianyusufabdullah.chatyuk.model.ModelChat;
import com.alfianyusufabdullah.chatyuk.model.ModelUser;
import com.alfianyusufabdullah.chatyuk.network.ChatRequest;
import com.alfianyusufabdullah.chatyuk.utils.Constan;
import com.alfianyusufabdullah.chatyuk.utils.EditTextListener;
import com.alfianyusufabdullah.chatyuk.utils.PreferencesManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends AppCompatActivity {

    @BindView(R.id.nav_user)
    TextView navUser;

    @BindView(R.id.nav_email)
    TextView navEmail;

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.mainDrawer)
    DrawerLayout drawerLayout;

    @BindView(R.id.btnSend)
    Button btnSend;

    @BindView(R.id.etMessage)
    EditText etMessage;

    @BindView(R.id.chatItem)
    RecyclerView chatItem;

    @OnClick(R.id.btnSignOut)
    public void signOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        builder.setTitle("Sign Out");
        builder.setMessage("Apakah Kamu Ingin Keluar ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                startActivity(new Intent(ActivityMain.this , ActivityLauncher.class));
                ActivityMain.this.finish();

            }
        });
        builder.setNegativeButton("NO" , null);
        builder.create().show();

        drawerLayout.closeDrawers();
        
    }

    AdapterChat adapterChat;
    List<ModelChat> listChat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bindUI();

        ChatRequest.getChat(new ChatRequest.OnChatRequest() {
            @Override
            public void result(ModelChat chat) {
                listChat.add(chat);

                if (listChat.size() > 100) {
                    listChat.remove(0);
                }

                adapterChat.notifyDataSetChanged();
            }
        });

    }

    private void bindUI() {

        adapterChat = new AdapterChat(listChat);

        chatItem.setHasFixedSize(true);
        chatItem.setLayoutManager(new LinearLayoutManager(this));
        chatItem.setAdapter(adapterChat);

        ModelUser user = PreferencesManager.initPreferences().getUserInfo();

        navUser.setText(user.getUsername());
        navEmail.setText(user.getEmail());

        toolbar.setTitle("Chat Yuk!");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        etMessage.addTextChangedListener(new EditTextListener(btnSend));
        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = MyApplication.getInstance().getSharedPreferences().getString(Constan.PREF_USERNAME, "user");
                String Message = etMessage.getText().toString();
                String Time = Constan.getTime();

                ModelChat chat = new ModelChat();
                chat.setUser(username);
                chat.setMessage(Message);
                chat.setTime(Time);

                ChatRequest.postMessage(chat);

                etMessage.setText("");
                MyApplication.hideSoftInput(ActivityMain.this , etMessage);
            }
        });


    }
}
