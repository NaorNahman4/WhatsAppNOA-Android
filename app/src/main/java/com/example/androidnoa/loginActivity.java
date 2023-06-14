package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidnoa.activities.RegisterActivity;
import com.example.androidnoa.activities.ShowAllUsersActivity;

public class loginActivity extends AppCompatActivity {
    public static appDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        //Initializing the appDB once
            db = Room.databaseBuilder(getApplicationContext(), appDB.class, "User").allowMainThreadQueries()
                    .build();


        Button btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, ContactsView.class);
                EditText username = findViewById(R.id.editTextUser);
                String usernameLabel = username.getText().toString();
                intent.putExtra("username", usernameLabel);
                startActivity(intent);
            }
        });
        Button btnRegister = findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //FOR TESTING THE LOCAL DB !!!
        Button btnShowAllUsers = findViewById(R.id.btnShowAllUsers);
        btnShowAllUsers.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowAllUsersActivity.class);
            startActivity(intent);
        });
    }
}