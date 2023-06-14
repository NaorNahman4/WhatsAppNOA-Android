package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.androidnoa.loginActivity.db;

import java.util.ArrayList;
import java.util.List;


public class ContactsView extends AppCompatActivity {
    private User activeUser;
    private int id;
    private int activeToken;
    List<Chat> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        //Getting the username of the user that logged in
        Intent prevIntent = getIntent();
        String username = prevIntent.getStringExtra("username");

        //Later on with the server we will get the token, with it will get the whole user
        //and will save it in activeUser field for later usage

        ListView lstFeed = (ListView) findViewById(R.id.lstContacts);

        contactList = generateContacts();
        final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this);
        lstFeed.setAdapter(feedAdapter);

        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsView.this, SettingsActivity.class);
                startActivityForResult(intent, 1);
                //here i want to get paramther from the settings activity

            }
        });

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                int logOutValue = data.getIntExtra("logOut", -1);
                if (logOutValue == 0) {
                    finish(); // Exit the app
                }
            }
        }
    }
    private List<Chat> generateContacts() {

        List<Chat> contacts = new ArrayList<>();

        User user1 = db.userDao().getUserById(1);
        User user2 = db.userDao().getUserById(5);
        User user3 = db.userDao().getUserById(1);
        User user4 = db.userDao().getUserById(18);

        List<User> chat1 = new ArrayList<>(); chat1.add(user2); chat1.add(user1);
        List<User> chat2 = new ArrayList<>(); chat2.add(user3); chat2.add(user2);
        List<User> chat3 = new ArrayList<>(); chat3.add(user4); chat3.add(user3);
        List<User> chat4 = new ArrayList<>(); chat4.add(user2); chat4.add(user4);

        contacts.add(new Chat(chat1));
        contacts.add(new Chat(chat2));
        contacts.add(new Chat(chat3));
        contacts.add(new Chat(chat4));

        return contacts;
    }

}