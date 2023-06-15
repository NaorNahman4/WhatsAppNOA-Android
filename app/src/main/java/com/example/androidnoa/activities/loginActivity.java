package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidnoa.R;
import com.example.androidnoa.api.UsersApi;
import com.example.androidnoa.appDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class loginActivity extends AppCompatActivity {

    public static appDB db;
    public static EditText editTextUser;
    public static EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button btnLogin = findViewById(R.id.buttonLogin);
        Button btnRegister = findViewById(R.id.buttonRegister);

        //Initialize Room
        db = Room.databaseBuilder(getApplicationContext(),
                appDB.class, "User")
                .fallbackToDestructiveMigration()
                .build();


        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUser = findViewById(R.id.editTextUser);
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String user = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                if (user.isEmpty() && password.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Please fill username and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Please fill username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsersApi usersApi = new UsersApi();
                usersApi.Login(user, password, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            String status = String.valueOf(response.code());
                            try {
                                String token = response.body().string();
                                if (status.equals("200")) {
                                    System.out.println("naor log in successful");
                                    System.out.println("naor token befor log in: " + token);
                                    // Open the activity and pass the token as an extra
                                    Intent intent = new Intent(loginActivity.this, ContactsView.class);
                                    intent.putExtra("token", token);
                                    intent.putExtra("user", user);
                                    startActivity(intent);
                                } else {
                                    // Handle the case when the status is not 200
                                    Toast.makeText(loginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(loginActivity.this, "Login request failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Handle network or API call failure
                        Toast.makeText(loginActivity.this, "Login request failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Settings
        FloatingActionButton btnSettings = findViewById(R.id.btnSettingLogin);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, SettingsInLoginActivity.class);
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

    @Override
    protected void onStop() {
        super.onStop();
        //Initialize empty strings in the next page
        loginActivity.editTextUser.setText("");
        loginActivity.editTextPassword.setText("");
    }

}