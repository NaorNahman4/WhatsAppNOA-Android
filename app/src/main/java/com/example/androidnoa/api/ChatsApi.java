package com.example.androidnoa.api;

import androidx.annotation.NonNull;

import com.example.androidnoa.Chat;
import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ChatsApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public WebServiceAPI getWebServiceAPI() {
        return webServiceAPI;
    }

    public void GetMyChats(String token, Callback<List<Chat>> callback){
        //make the string without the first and the last chars
        token = token.substring(1, token.length() - 1);
        System.out.println("naor token inside" + token);
        Call<List<Chat>> call = webServiceAPI.GetChats(token);
        call.enqueue(callback);
    }
}
