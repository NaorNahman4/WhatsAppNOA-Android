package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidnoa.R;

public class SettingsInLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the theme before calling setContentView()
        //setTheme(androidx.appcompat.R.style.Widget_AppCompat_ActionBar_Solid);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_in_login);



        Button btnBlack = findViewById(R.id.darkThemeRadioButton);
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Change to the dark theme
            }
        });

        Button btnWhite = findViewById(R.id.lightThemeRadioButton);
        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Change to the light theme
            }
        });
        Button btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}