package com.alfianyusufabdullah.chatyuk.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alfianyusufabdullah.chatyuk.MyApplication;
import com.alfianyusufabdullah.chatyuk.R;
import com.alfianyusufabdullah.chatyuk.model.ModelChat;
import com.alfianyusufabdullah.chatyuk.utils.Constan;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

public class HolderChat extends RecyclerView.ViewHolder {

    @BindViews({R.id.cardFrom, R.id.cardTo})
    List<CardView> cardChat;

    @BindViews({R.id.fromUsername, R.id.toUsername})
    List<TextView> username;

    @BindViews({R.id.fromMessage, R.id.toMessage})
    List<TextView> message;

    @BindViews({R.id.fromTime, R.id.toTime})
    List<TextView> time;

    public HolderChat(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(ModelChat chat) {

        String User = chat.getUser();
        String user = MyApplication.getInstance().getSharedPreferences().getString(Constan.PREF_USERNAME, "user");

        int POS_FROM = 0;
        int POS_TO = 1;

        if (User.equals(user)) {

            cardChat.get(POS_TO).setVisibility(View.VISIBLE);
            cardChat.get(POS_FROM).setVisibility(View.GONE);

            username.get(POS_TO).setText(chat.getUser());
            message.get(POS_TO).setText(chat.getMessage());
            time.get(POS_TO).setText(chat.getTime());

        } else {

            cardChat.get(POS_TO).setVisibility(View.GONE);
            cardChat.get(POS_FROM).setVisibility(View.VISIBLE);

            username.get(POS_FROM).setText(chat.getUser());
            message.get(POS_FROM).setText(chat.getMessage());
            time.get(POS_FROM).setText(chat.getTime());
        }
    }
}