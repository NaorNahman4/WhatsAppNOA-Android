package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
            ChatsApi chatsApi = new ChatsApi("10.0.2.2");
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
                        showCustomToast("User is not exist");
                    }
                    else if(resCode == 501){
                        showCustomToast("Cannot add yourself");
                    }else if(resCode == 500){
                        showCustomToast("Chat exists already");
                    }
                    else{
                        showCustomToast("Unrecognized Error");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Chat> call, @NonNull Throwable t) {
                    showCustomToast("Invalid server IP / no good communication!");
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
    public  void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_warning,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 32);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}