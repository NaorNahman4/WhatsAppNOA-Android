package com.example.androidnoa;


import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @POST("Users")
    Call<ResponseBody> registerUser(@Body UserRegistrationRequest request);

    @POST("Tokens")
    Call<ResponseBody> logInUser(@Body UserNameAndPass data);

//
//    @POST("api/LogIn")
//    Call<JsonPrimitive> logIn(@Body Login login);
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