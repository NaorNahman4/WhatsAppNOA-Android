package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidnoa.Chat;
import com.example.androidnoa.ContactAdapter;
import com.example.androidnoa.Message;
import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.adapters.ChatAdapter;
import com.example.androidnoa.api.ChatsApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsView extends AppCompatActivity {
    List<Chat> contactList;
    List<Message> msg;
    User currentUser;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        // Retrieve the intent that started this activity
        Intent lastIntent = getIntent();

        // Retrieve the token value from the intent
        token = lastIntent.getStringExtra("token");
        String username = lastIntent.getStringExtra("username");
        currentUser = (User) lastIntent.getSerializableExtra("user");


        ListView lstFeed = findViewById(R.id.lstContacts);
        ChatsApi chatsApi = new ChatsApi();
        chatsApi.GetMyChats(token, new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    List<Chat> chats = response.body();
                    contactList = chats;
                    final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this, username);
                    lstFeed.setAdapter(feedAdapter);
                } else {
                    // Handle unsuccessful response
                    System.out.println("naor get my chats unsuccessful getmyChats");
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.out.println("naor failed to get my chats getmyChats");
                // Handle failure
            }
        });

        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            int chatId = contactList.get(i).getId();


            chatsApi.GetMessagesByChatId(token, chatId,(new Callback<List<Message>>() {
                @Override
                public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                    if (response.isSuccessful()) {
                       msg = response.body();
                       if(msg != null){
                       handleResponse(msg, chatId);
                       }
                    } else {
                        // Handle unsuccessful response
                        System.out.println("Cant get messages- unsuccesfull");
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    System.out.println("naor failed to get my chats");
                    // Handle failure
                }
            }));
            });
        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsView.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent intent = new Intent(this, AddChatActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Retrieve the intent that started this activity
        Intent lastIntent = getIntent();
    }

    public void handleResponse(List<Message> msgList,int chatId) {
//        List<String> list = new ArrayList<>();
//        for (Message m : msgList) {
//            list.add(m.getContent());
//        }
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("list", (ArrayList<Message>) msgList);
        intent.putExtra("user", (User) currentUser);
        intent.putExtra("token", (String) token);
        intent.putExtra("chatId", (int) chatId);
        startActivity(intent);
    }
}

