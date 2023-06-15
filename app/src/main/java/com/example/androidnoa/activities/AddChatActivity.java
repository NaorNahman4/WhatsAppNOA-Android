package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidnoa.Chat;
import com.example.androidnoa.R;
import com.example.androidnoa.UserDao;
import com.example.androidnoa.api.ChatsApi;
import com.example.androidnoa.appDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChatActivity extends AppCompatActivity {
    private UserDao userDao;
    private String token;
    private Chat newChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        token = getIntent().getStringExtra("token");
        Button addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(view -> {
            EditText chatName = findViewById(R.id.etContactName);
            String chatNameStr = chatName.getText().toString();
            ChatsApi chatsApi = new ChatsApi();
            chatsApi.CreateMyChat(token, chatNameStr , new Callback<Chat>() {
                @Override
                public void onResponse(@NonNull Call<Chat> call, @NonNull Response<Chat> response) {
                    System.out.println("naor create chat response: ");
                    System.out.println("naor"  + response.code());
                    if (response.isSuccessful()) {
                        System.out.println("naor create chat successful");
                        newChat = response.body();
                        new Thread(() -> {
                            db.chatDao().insert(response.body());
                        }).start();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Chat> call, @NonNull Throwable t) {
                    System.out.println("naor create chat failed");
                    System.out.println("naor"  + t.getMessage());
                }
            });
            finish();
        });
    }
}