package com.example.androidnoa.activities;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.UserDao;
import com.example.androidnoa.api.UsersApi;
import com.example.androidnoa.appDB;

public class RegisterActivity extends AppCompatActivity {
    private appDB db;
    private UserDao userDao;
    int PICK_IMAGE = 1;
    ImageView imageViewPicture;
    Button buttonAddPicture;
    ActivityResultLauncher<Intent> galleryLauncher;
    private Bitmap defaultPictureBitmap;



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
        defaultPictureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_pic);

        btnRegister.setOnClickListener(v -> {
            // Get the values from the input fields
            EditText editTextUser = findViewById(R.id.editTextUser);
            EditText editTextPassword = findViewById(R.id.editTextPassword);
            EditText editTextDisplayName = findViewById(R.id.editTextDisplayName);
            String img;
            if (imageViewPicture == null) {
                // Use the default picture if no image is selected
                img = encodeImageToBase64(defaultPictureBitmap);
            } else {
                imageViewPicture.setDrawingCacheEnabled(true); // Enable drawing cache
                imageViewPicture.buildDrawingCache(); // Build the cache
                Bitmap drawingCache = imageViewPicture.getDrawingCache();
                if (drawingCache != null) {
                    img = encodeImageToBase64(drawingCache);
                } else {
                    // Use the default picture if the cache is null
                    img = encodeImageToBase64(defaultPictureBitmap);
                }
            }

            String user = editTextUser.getText().toString();
            String password = editTextPassword.getText().toString();
            String displayName = editTextDisplayName.getText().toString();

            UsersApi usersApi = new UsersApi();
            usersApi.Register(user, password, displayName, img);
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
    private String encodeImageToBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private User addUserLocaly(String username, String password, String disName){
        //Giving all the users this picture
        int default_pic = getResources().getIdentifier("default_pic", "drawable", getPackageName());
        User newUser = new User(username, password, disName, "");
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



