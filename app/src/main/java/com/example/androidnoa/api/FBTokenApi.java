package com.example.androidnoa.api;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;
import com.example.androidnoa.models.FBToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FBTokenApi {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public FBTokenApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void sendTokenToServer2(String username, String token, Callback<ResponseBody> callback) {
        FBToken request = new FBToken(token,username);
        Call<ResponseBody> call = webServiceAPI.sendTokenToServer(request);
        call.enqueue(callback);
    }
    public void logOutMyUser(String username,String fbToken, Callback<ResponseBody> callback) {
        FBToken request = new FBToken(fbToken,username);
        Call<ResponseBody> call = webServiceAPI.logOutUser(request);
        call.enqueue(callback);
    }
}
