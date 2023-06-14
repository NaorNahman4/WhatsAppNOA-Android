package com.example.androidnoa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.androidnoa.Chat;
import com.example.androidnoa.ContactAdapter;
import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.api.ChatsApi;
import com.example.androidnoa.api.UsersApi;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        // Retrieve the intent that started this activity
        Intent lastIntent = getIntent();

        // Retrieve the token value from the intent
        String token = lastIntent.getStringExtra("token");
        String userName = lastIntent.getStringExtra("user");

        ListView lstFeed = findViewById(R.id.lstContacts);
        ChatsApi chatsApi = new ChatsApi();
        chatsApi.GetMyChats(token, new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                System.out.println("naor get my chats response: ");
                System.out.println("naor"  + response.code());
                if (response.isSuccessful()) {
                    System.out.println("naor get my chats successful");
                    List<Chat> chats = response.body();
                    contactList = chats;
                    final ContactAdapter feedAdapter = new ContactAdapter(contactList,
                            ContactsView.this,
                            userName);
                    lstFeed.setAdapter(feedAdapter);
                } else {
                    // Handle unsuccessful response
                    System.out.println("naor get my chats unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.out.println("naor failed to get my chats");
                // Handle failure
            }
        });


        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("displayName", contactList.get(i).getOtherDisplayName(userName));
            startActivity(intent);

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

        // Retrieve the token value from the intent
        String token = lastIntent.getStringExtra("token");
        String userName = lastIntent.getStringExtra("user");

        ListView lstFeed = findViewById(R.id.lstContacts);
        ChatsApi chatsApi = new ChatsApi();
        chatsApi.GetMyChats(token, new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                System.out.println("naor get my chats response: ");
                System.out.println("naor" + response.code());
                if (response.isSuccessful()) {
                    System.out.println("naor get my chats successful");
                    List<Chat> chats = response.body();
                    contactList = chats;
                    final ContactAdapter feedAdapter = new ContactAdapter(contactList,
                            ContactsView.this,
                            userName);
                    lstFeed.setAdapter(feedAdapter);
                } else {
                    // Handle unsuccessful response
                    System.out.println("naor get my chats unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.out.println("naor failed to get my chats");
                // Handle failure
            }
        });
    }
}