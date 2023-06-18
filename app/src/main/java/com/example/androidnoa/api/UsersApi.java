package com.example.androidnoa.api;



import static com.example.androidnoa.activities.loginActivity.db;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.models.UserName;
import com.example.androidnoa.models.UserNameAndPass;
import com.example.androidnoa.models.UserAllDetails;

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

    public void Register(User user,Callback<ResponseBody> callback){
        Call<ResponseBody> call = webServiceAPI.registerUser(user);
        call.enqueue(callback);
    }
    public void Login(String username, String password, Callback<ResponseBody> callback){
        UserNameAndPass data = new UserNameAndPass(username, password);
        Call<ResponseBody> call = webServiceAPI.logInUser(data);
        call.enqueue(callback);
    }

    public void GetMyDetails(String username,String token, Callback<User> callback){
        //make the string without the first and the last chars
        token = token.substring(1, token.length() - 1);
        //UserName userStr = new UserName(username);
        Call<User> call = webServiceAPI.getUser(username, token);
        call.enqueue(callback);
    }




}