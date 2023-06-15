package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.api.UsersApi;
import com.example.androidnoa.appDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class loginActivity extends AppCompatActivity {

    public static appDB db;
    public static EditText editTextUser;
    public static EditText editTextPassword;
    private User currectUser;
    private List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        usersList=new ArrayList<>();

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button btnLogin = findViewById(R.id.buttonLogin);
        Button btnRegister = findViewById(R.id.buttonRegister);

        //Initialize Room
        db = Room.databaseBuilder(getApplicationContext(),
                appDB.class, "User")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUser = findViewById(R.id.editTextUser);
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String username = editTextUser.getText().toString();
                Thread backgroundThread = new Thread(() -> {
                    if (db != null && db.userDao() != null) {
                        usersList = db.userDao().index();
                    }
                });
                backgroundThread.start(); // Start the background thread
                try {
                    backgroundThread.join(); // Wait for the background thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    currectUser=null;
                    for (User user : usersList) {
                        if(user.getUsername().equals(username)){
                            currectUser=user;
                        }
                    }
                String password = editTextPassword.getText().toString();
                if (username.isEmpty() && password.isEmpty()) {
                    showCustomToast("Please fill username and password");
                    return;
                }
                if (username.isEmpty()) {
                    showCustomToast("Please fill username");
                    return;
                }
                if (password.isEmpty()) {
                    showCustomToast("Please fill password");
                    return;
                }
                UsersApi usersApi = new UsersApi();
                usersApi.Login(username, password, new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            String status = String.valueOf(response.code());
                            try {
                                String token = response.body().string();

                                if (status.equals("200")) {
                                    // Open the activity and pass the token as an extra
                                    Intent intent = new Intent(loginActivity.this, ContactsView.class);
                                    intent.putExtra("token", token);
                                    intent.putExtra("username", username);
                                    intent.putExtra("user", currectUser);
                                    startActivity(intent);
                                } else {
                                    // Handle the case when the status is not 200
                                    showCustomToast("Login request failed");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Handle unsuccessful response
                            showCustomToast("Login request failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Handle network or API call failure
                        showCustomToast("Login request failed");
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
    public  void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_warning,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 32);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}