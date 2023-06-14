package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.androidnoa.ContactsView;
import com.example.androidnoa.R;
import com.example.androidnoa.UserDao;
import com.example.androidnoa.appDB;

public class AddChatActivity extends AppCompatActivity {
    private appDB db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        Button addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(view -> {

            Intent intent = new Intent(this, ContactsView.class);
            startActivity(intent);
        });
    }
}