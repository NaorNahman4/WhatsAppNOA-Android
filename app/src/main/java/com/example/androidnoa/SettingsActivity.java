package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the theme before calling setContentView()
        //setTheme(androidx.appcompat.R.style.Widget_AppCompat_ActionBar_Solid);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



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
        Button btnLogout = findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("logOut", 0);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}
