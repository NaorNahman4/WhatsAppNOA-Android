package com.example.androidnoa.activities;


import static com.example.androidnoa.activities.loginActivity.ServerIP;
import static com.example.androidnoa.activities.loginActivity.db;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.UserDao;
import com.example.androidnoa.api.UsersApi;
import com.example.androidnoa.appDB;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    int PICK_IMAGE = 1;
    ImageView imageViewPicture;
    Bitmap imageBitmap;
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

            if (imageViewPicture.getDrawable() != null && imageViewPicture.getDrawable() instanceof BitmapDrawable) {
                // convert image to bitmap and then to base64
                Bitmap tempBit = imageViewToBitmap(imageViewPicture);
                img = bitmapToBase64(tempBit);
                img = "data:text/html;base64," + img;
                //img = convertImageViewToBase64(imageViewPicture);
            } else {
                // Use the default picture if no image is selected
                ImageView tmp = new ImageView(this);
                tmp.setImageResource(R.drawable.default_pic);
                // convert image to bitmap and then to base64
                Bitmap bitmap = ((BitmapDrawable) tmp.getDrawable()).getBitmap();

                img = convertImageViewToBase64(tmp);
                img = "data:text/html;base64," + img;
                }
            String user = editTextUser.getText().toString();
            String password = editTextPassword.getText().toString();
            String displayName = editTextDisplayName.getText().toString();
            if(user.isEmpty()){
                showCustomToast("Please fill username");
                return;
            }
            if(password.isEmpty()){
                showCustomToast("Please fill password");
                return;
            }
            if(displayName.isEmpty()){
                showCustomToast("Please fill display name");
                return;
            }
            if(displayName.length() > 20){
                showCustomToast("Display name is too long");
                return;
            }
            UsersApi usersApi = new UsersApi(ServerIP);
            User request = new User(user, password, displayName, img);

            usersApi.Register(request, new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        new Thread(() ->{
                            db.userDao().insert(request);
                        }).start();
                    }
                    else {
                        showCustomToast("User already existwfegetgegs");
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showCustomToast("Invalid server call!");
                }
            });
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
        int compressionQuality = 30; // Adjust the compression quality as needed (0-100)

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
    public Bitmap imageViewToBitmap(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }
    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }
    public String convertImageViewToBase64(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    // Method to convert a Drawable to a Bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}



