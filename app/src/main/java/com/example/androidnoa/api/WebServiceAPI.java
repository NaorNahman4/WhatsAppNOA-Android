package com.example.androidnoa.api;


import com.example.androidnoa.Chat;
import com.example.androidnoa.Message;
import com.example.androidnoa.User;

import com.example.androidnoa.models.FBToken;
import com.example.androidnoa.models.MesStr;
import com.example.androidnoa.models.UserName;

import com.example.androidnoa.models.UserNameAndPass;
import com.example.androidnoa.models.UserAllDetails;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServiceAPI {

    @POST("Users")
    Call<ResponseBody> registerUser(@Body User request);

    @POST("Tokens")
    Call<ResponseBody> logInUser(@Body UserNameAndPass data);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("Authorization") String token);
    @GET("Chats")
    Call<List<Chat>> GetChats(@Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> GetMessagesByChatId(@Header("Authorization") String token,@Path("id") int id);

    @POST("Chats/{id}/Messages")
    Call<Message> sendMessage(@Path("id") int id, @Body MesStr message, @Header("Authorization") String token);

    @POST("Chats")
    Call<Chat> CreateChat(@Body UserName userName, @Header("Authorization") String token);

    @DELETE("Chats/{id}")
    Call<ResponseBody> deleteChat(@Header("Authorization") String token,@Path("id") int id);

    @POST("TokensFB")
    Call<ResponseBody> sendTokenToServer(@Body FBToken data);
    @POST("TokensFB/logout")
    Call<ResponseBody> logOutUser(@Body FBToken data);
}