package com.example.androidnoa;


import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UsersApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public WebServiceAPI getWebServiceAPI() {
        return webServiceAPI;
    }

    public void Register(String username, String password, String name, String img){
        UserRegistrationRequest request = new UserRegistrationRequest(username, password, name, img);
        Call<ResponseBody> call = webServiceAPI.registerUser(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Toast.makeText(MyApplication.context, "User registered successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MyApplication.context, "User is taken", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("On failure ");
            }
        });
    }
    public void Login(String username, String password){
        UserNameAndPass data = new UserNameAndPass(username, password);
        Call<ResponseBody> call = webServiceAPI.logInUser(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        // Get the response body as a string
                        String status = String.valueOf(response.code());
                        System.out.println(status);
                    try {
                        String token = response.body().string();
                        System.out.println(token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Handle unsuccessful response
                    System.out.println("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("On failure ");
            }
        });
    }

}
