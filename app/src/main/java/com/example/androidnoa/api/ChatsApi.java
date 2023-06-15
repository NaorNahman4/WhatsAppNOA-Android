package com.example.androidnoa.api;

import static com.example.androidnoa.activities.loginActivity.db;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidnoa.Chat;
import com.example.androidnoa.ContactAdapter;
import com.example.androidnoa.Message;
import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;

import com.example.androidnoa.models.Id;

import com.example.androidnoa.models.UserName;


import java.util.ArrayList;
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

        retrofit= new Retrofit.Builder()
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
        Call<List<Chat>> call = webServiceAPI.GetChats(token);
        call.enqueue(callback);
    }

    //Add new Chat
    public void CreateMyChat(String token, String userName,  Callback<Chat> callback){
        //make the string without the first and the last chars
        token = token.substring(1, token.length() - 1);
        UserName userName1 = new UserName(userName);
        Call<Chat> call = webServiceAPI.CreateChat(userName1, token);
        call.enqueue(callback);
    }


    public void GetMessagesByChatId(String token, int id, Callback<List<Message>> callback){
        //make the string without the first and the last chars
        token = token.substring(1, token.length() - 1);

        Call<List<Message>> call = webServiceAPI.GetMessagesByChatId(token, id);
        call.enqueue(callback);

    }


}
