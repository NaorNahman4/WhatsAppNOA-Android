package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AddChatActivity extends AppCompatActivity {

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