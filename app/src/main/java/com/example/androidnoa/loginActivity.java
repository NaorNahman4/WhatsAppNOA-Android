package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        Button btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUser = findViewById(R.id.editTextUser);
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String user = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                UsersApi usersApi = new UsersApi();

                usersApi.Login(user, password);

                Intent intent2 = new Intent(loginActivity.this, ContactsView.class);
                startActivity(intent2);
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