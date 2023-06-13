package com.example.androidnoa;


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
        System.out.println("web is :" + webServiceAPI);
        Call<ResponseBody> call = webServiceAPI.registerUser(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("naor User registered successfully");
                int statusCode = response.code();
                if (statusCode == 200) {
                    System.out.println("naor User registered successfully");
                }
                else {
                    System.out.println("naor User registered failed");
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Failed to register user44444444444");
            }
        });
    }

}
