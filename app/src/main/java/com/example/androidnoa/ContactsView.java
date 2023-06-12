package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ContactsView extends AppCompatActivity {

    List<Chat> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        ListView lstFeed = (ListView) findViewById(R.id.lstContacts);

        contactList = generateContacts();
        final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this);
        lstFeed.setAdapter(feedAdapter);

        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
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
        User user1 = new User("oror", "oror", "Or Haroni", default_pic);
        User user2 = new User("123", "123", "Naor Nahman", default_pic);
        User user3 = new User("1234", "1234", "Adar Katz", default_pic);
        User user4 = new User("12345", "12345", "Hemi Lebovich", default_pic);
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