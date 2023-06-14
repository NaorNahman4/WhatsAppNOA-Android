package com.example.androidnoa.activities;


import static com.example.androidnoa.activities.loginActivity.db;

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
                    final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this);
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


//        contactList = generateContacts();
//        final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this);
//        lstFeed.setAdapter(feedAdapter);
//
        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, ChatActivity.class);
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
            startActivity(intent);
        });
    }

    private List<Chat> generateContacts() {

        List<Chat> contacts = new ArrayList<>();

        int default_pic = getResources().getIdentifier("default_pic", "drawable", getPackageName());
        User user1 = db.userDao().getUserById(1);
        User user2 = db.userDao().getUserById(2);
        User user3 = db.userDao().getUserById(3);
        User user4 = db.userDao().getUserById(4);
        ArrayList<User> chat1 = new ArrayList<>(); chat1.add(user1); chat1.add(user2);
        ArrayList<User> chat2 = new ArrayList<>(); chat2.add(user1); chat2.add(user3);
        ArrayList<User> chat3 = new ArrayList<>(); chat3.add(user1); chat3.add(user4);
        ArrayList<User> chat4 = new ArrayList<>(); chat4.add(user2); chat4.add(user3);

        contacts.add(new Chat(chat1));
        contacts.add(new Chat(chat2));
        contacts.add(new Chat(chat3));
        contacts.add(new Chat(chat4));

        return contacts;
    }

}