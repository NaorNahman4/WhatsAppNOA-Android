package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;


public class ContactsView extends AppCompatActivity {

    List<Contact> contactList;

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
    }

    private List<Contact> generateContacts() {
        List<Contact> contacts = new ArrayList<>();

        int default_pic = getResources().getIdentifier("default_pic", "drawable", getPackageName());
        contacts.add(new Contact(1,
                "Or Haroni", "Last Message", "Date", default_pic));
        contacts.add(new Contact(2,
                "Naor Nahman", "Last Message", "Date"));
        contacts.add(new Contact(3,
                "Adar Katz", "Last Message", "Date", default_pic));

        return contacts;
    }

}