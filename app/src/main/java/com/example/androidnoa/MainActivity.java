package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Openning the CONTACTS LIST
        Intent intent = new Intent(MainActivity.this, ContactsView.class);
        startActivity(intent);
    }
}