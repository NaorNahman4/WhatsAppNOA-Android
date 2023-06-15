package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidnoa.Chat;
import com.example.androidnoa.ContactAdapter;
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
    private Thread t;

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
            t =  new Thread(() -> {
                db.chatDao().insert(newChat);
                runOnUiThread(() -> {
                    finish();
                });
            });
            chatsApi.CreateMyChat(token, chatNameStr , new Callback<Chat>() {
                @Override
                public void onResponse(@NonNull Call<Chat> call, @NonNull Response<Chat> response) {
                    System.out.println("naor create chat response: ");
                    System.out.println("naor"  + response.code());
                    int resCode = response.code();
                    if (response.isSuccessful()) {
                        System.out.println("naor create chat successful");
                        newChat = response.body();
                       t.start();
                    }
                    else if(resCode == 404){
                        Toast.makeText(AddChatActivity.this, "User is not exist"
                        ,Toast.LENGTH_LONG).show();
                    }
                    else if(resCode == 501){
                        Toast.makeText(AddChatActivity.this, "Cannot add yourself"
                                ,Toast.LENGTH_LONG).show();
                    }else if(resCode == 500){
                        Toast.makeText(AddChatActivity.this, "Chat exists already"
                                ,Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(AddChatActivity.this, "Unrecognized Error"
                                ,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Chat> call, @NonNull Throwable t) {
                    System.out.println("naor create chat failed");
                    System.out.println("naor"  + t.getMessage());
                    finish();
                }
            });
            try {
                t.join();
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}