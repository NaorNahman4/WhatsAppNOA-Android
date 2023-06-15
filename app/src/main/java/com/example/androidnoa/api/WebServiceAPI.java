package com.example.androidnoa.api;


import com.example.androidnoa.Chat;
import com.example.androidnoa.Message;
import com.example.androidnoa.User;

import com.example.androidnoa.models.MesStr;
import com.example.androidnoa.models.UserName;

import com.example.androidnoa.models.UserNameAndPass;
import com.example.androidnoa.models.UserAllDetails;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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



//
//    @POST("api/ConnectToFirebase")
//    Call<Void> ConnectToFirebase(@Body UserFBToken userFBToken);
//
//
//
//    @GET("api/Contacts")
//    Call<List<Contact>> getAllContacts(@Header("authorization") String auth);
//
//    @POST("api/Contacts")
//    Call<Void> postContact(@Body Contact contact , @Header("authorization") String auth);
//
//    @POST("api/Invitations")
//    Call<Void> postInvitation(@Body Invitation invitation);
//
//
//    @GET("api/Contacts/{id}/Messages")
//    Call<List<Message>> getMessages(@Path("id") String id, @Header("authorization") String auth);
//
//    @POST("api/Contacts/{id}/Messages")
//    Call<Void> postMessage(@Path("id") String id, @Body ApiMessage message,
//                           @Header("authorization") String auth);
//
//    @POST("api/Transfer")
//    Call<Void> transfer(@Body Transfer transfer);
}