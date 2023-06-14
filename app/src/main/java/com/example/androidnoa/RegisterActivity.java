package com.example.androidnoa;


import static com.example.androidnoa.loginActivity.db;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static com.example.androidnoa.loginActivity.db;

public class RegisterActivity extends AppCompatActivity {
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
                // Execute the database transaction on a separate thread
                new Thread(() -> {
                    addUserLocaly(user, password, displayName);

                    // Instead of creating more intents, just return to the last one
                    runOnUiThread(() -> {
                        finish();
                    });
                }).start();
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

    private User addUserLocaly(String username, String password, String disName) {
        // Giving all the users this picture
        int default_pic = getResources().getIdentifier("default_pic", "drawable", getPackageName());
        ImageView img = findViewById(R.id.imageViewPicture);
        int pic = convertUploadedImageToInt(img);
        User newUser = new User(username, password, disName, pic);
        try {
            userDao = db.userDao();
            //Adding the user to the data
            AsyncTask.execute(() -> {
                    userDao.insert(newUser);
            });
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int convertUploadedImageToInt(ImageView img) {
        if(img.getDrawable() == null){
            return 0;
        }
        Drawable drawable = img.getDrawable();
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmap = bitmapDrawable.getBitmap();
        }

        // Convert the Bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Save the byte array to internal storage
        String fileName = "uploaded_image.png";
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the resource identifier for the saved image file
        int resourceId = getResources().getIdentifier(fileName, "drawable", getPackageName());

        // Return the resource identifier
        return resourceId;
    }


}



