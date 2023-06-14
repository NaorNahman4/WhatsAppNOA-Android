package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidnoa.R;
import com.example.androidnoa.api.UsersApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

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