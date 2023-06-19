package com.example.androidnoa.api;

import static com.example.androidnoa.activities.loginActivity.db;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidnoa.MyApplication;
import com.example.androidnoa.User;
import com.example.androidnoa.models.FBToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FBTokenApi {


    public void sendTokenToServer(String username, String token, Callback<ResponseBody> callback){
        FBToken request = new FBToken(username, token);
        WebServiceAPI webServiceAPI = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(com.example.androidnoa.R.string.BaseURL))
                .build()
                .create(WebServiceAPI.class);
        Call<ResponseBody> call = webServiceAPI.sendTokenToServer(request);
        call.enqueue(callback);
    }
}
