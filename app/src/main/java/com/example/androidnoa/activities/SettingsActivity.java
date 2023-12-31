package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.ServerIP;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnoa.R;
import com.example.androidnoa.activities.loginActivity;
import com.example.androidnoa.api.FBTokenApi;
import com.example.androidnoa.api.UsersApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    private String fbToken;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the theme before calling setContentView()
        //setTheme(androidx.appcompat.R.style.Widget_AppCompat_ActionBar_Solid);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        fbToken = intent.getStringExtra("FBtoken");
        username = intent.getStringExtra("username");
        Button btnBlack = findViewById(R.id.darkThemeRadioButton);
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Change to the dark theme
                FBTokenApi  fbTokenApi = new FBTokenApi();
                fbTokenApi.sendTokenToServer2(username, fbToken, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showCustomToast("Failed to send token to server");
                    }
                } );
            }

        });

        Button btnWhite = findViewById(R.id.lightThemeRadioButton);
        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Change to the light theme
                FBTokenApi  fbTokenApi = new FBTokenApi();
                fbTokenApi.sendTokenToServer2(username, fbToken, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showCustomToast("Failed to send token to server");
                    }
                } );
            }
        });

        Button btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText serverIpEditText = findViewById(R.id.serverIpEditText);
                if (serverIpEditText.getText().toString().equals("")) {
                    showCustomToast("Cant save empty ip");
                } else {
                    ServerIP = serverIpEditText.getText().toString();
                    finish();
                }
            }
        });
        Button btnLogout = findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("logOut", ContactsView.REQUEST_LOGOUT); // Indicate logout action
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

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
