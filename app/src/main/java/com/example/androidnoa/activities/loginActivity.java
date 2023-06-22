package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.example.androidnoa.api.FBTokenApi;
import com.example.androidnoa.api.UsersApi;
import com.example.androidnoa.appDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class loginActivity extends AppCompatActivity {
    public static String ServerIP = "10.0.2.2";

    public static appDB db;
    public static EditText editTextUser;
    public static EditText editTextPassword;
    private User currectUser;
    private List<User> usersList;
    private static final int NOTIFICATION_ID = 1;
    private String FBtoken;
    private static final String CHANNEL_ID = "channel_id";
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        usersList = new ArrayList<>();
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
                Intent intent2 = new Intent(loginActivity.this, ContactsView.class);
                UsersApi usersApi = new UsersApi(ServerIP);
                usersApi.Login(username, password, new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            String status = String.valueOf(response.code());
                            try {
                                String token = response.body().string();
                                if (status.equals("200")) {
                                    usersApi.GetMyDetails(username, token, new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            if(response.isSuccessful()){
                                                currectUser = response.body();
                                                intent2.putExtra("token", token);
                                                intent2.putExtra("user", currectUser);
                                                intent2.putExtra("username", username);
                                                //Fire base sent token.

                                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(loginActivity.this, instanceIdResult -> {
                                                    FBtoken = instanceIdResult.getToken();
                                                    intent2.putExtra("FBtoken", FBtoken);
                                                    FBTokenApi fbTokenApi = new FBTokenApi();
                                                    fbTokenApi.sendTokenToServer2(username, FBtoken, new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            if (response.isSuccessful()) {
                                                                String status = String.valueOf(response.code());
                                                                if (status.equals("200")) {
                                                                    startActivity(intent2); // Move startActivity here
                                                                } else {
                                                                    // Handle the case when the status is not 200
                                                                    showCustomToast("Error in FireBase");
                                                                }
                                                            } else {
                                                                // Handle unsuccessful response
                                                                showCustomToast("Error with the server");
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                            // Handle network or API call failure
                                                            showCustomToast("Login request failed");
                                                        }
                                                    });
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            showCustomToast("Error with the server");
                                        }
                                    });
                                    //I get here and its start without wait for intent2.putExtra("FBtoken", FBtoken);
                                    //startActivity(intent2);
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
                        showCustomToast("Invalid server call!");
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

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Initialize empty strings in the next page
        loginActivity.editTextUser.setText("");
        loginActivity.editTextPassword.setText("");
    }
    public void showCustomToast(String message) {
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