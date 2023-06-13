package com.example.androidnoa;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.FileNotFoundException;
import java.io.InputStream;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    private appDB db;
    private UserDao userDao;
    int PICK_IMAGE = 1;
    ImageView imageViewPicture;
    Button buttonAddPicture;
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        imageViewPicture = findViewById(R.id.imageViewPicture);
        buttonAddPicture = findViewById(R.id.buttonAddPicture);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnAlreadyHaveAnAccount = findViewById(R.id.btnAlreadyHaveAnAccount);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImage = data.getData();
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                imageViewPicture.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        buttonAddPicture.setOnClickListener(v -> openGallery());

        buttonAddPicture.setOnClickListener(v -> openGallery());
        btnRegister.setOnClickListener(v -> {
            // Get the values from the input fields
            EditText editTextUser = findViewById(R.id.editTextUser);
            EditText editTextPassword = findViewById(R.id.editTextPassword);
            EditText editTextDisplayName = findViewById(R.id.editTextDisplayName);

            String user = editTextUser.getText().toString();
            String password = editTextPassword.getText().toString();
            String displayName = editTextDisplayName.getText().toString();

            // Create a JSON object with the user data
//            JSONObject json = new JSONObject();
            try {
                AsyncTask.execute(() -> {
                    //Try to add the user to UserDao
                    addUserLocaly(user, password,displayName);
                });
//                json.put("username", user);
//                json.put("password", password);
//                json.put("displayName", displayName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Execute the RegisterServerTask
//            RegisterServerTask registerServerTask = new RegisterServerTask();
//            registerServerTask.execute(json.toString());

            //Instead of create more intents, just return to the last one
            finish();
        });
        btnAlreadyHaveAnAccount.setOnClickListener(v -> {
            //Instead of create more intents, just return to the last one
            finish();
        });
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }
    private User addUserLocaly(String username, String password, String disName){
        //Giving all the users this picture
        int default_pic = getResources().getIdentifier("default_pic", "drawable", getPackageName());
        User newUser = new User(username, password, disName, default_pic);
        try{
            db = Room.databaseBuilder(getApplicationContext(),
                            appDB.class, "UsersDB")
                    .build();
                userDao = db.userDao();
                db.userDao().insert(newUser);

            return newUser;

        } catch(Exception e){
            return null;
        }

    }
}



