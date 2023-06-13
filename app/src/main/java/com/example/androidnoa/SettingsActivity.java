package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        RadioButton btnBlack = findViewById(R.id.darkThemeRadioButton);
//        btnBlack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTheme(R.style.Base_Theme_AndroidNOA_Dark); // Change to the dark theme
//                recreate(); // Recreate the activity to apply the new theme
//            }
//        });
//        RadioButton btnWhite = findViewById(R.id.lightThemeRadioButton);
//        btnWhite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTheme(R.style.Base_Theme_AndroidNOA); // Change to the light theme
//                recreate(); // Recreate the activity to apply the new theme
//            }
//        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
        });
    }

}