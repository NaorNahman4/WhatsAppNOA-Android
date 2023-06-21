package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.ServerIP;
import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidnoa.Chat;
import com.example.androidnoa.ChatComparator;
import com.example.androidnoa.ContactAdapter;
import com.example.androidnoa.Message;
import com.example.androidnoa.MyApplication;
import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.adapters.ChatAdapter;
import com.example.androidnoa.api.ChatsApi;

import com.example.androidnoa.api.FBTokenApi;
import com.example.androidnoa.api.UsersApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsView extends AppCompatActivity {
    private Thread updateThread;
    List<Chat> contactList;
    List<Message> msg;
    User currentUser;
    UsersApi userApi;
    private String token;
    private String userName;
    private ListView lstFeed;
    private ChatsApi chatsApi;
    private String fbToken;
    public static final int REQUEST_SETTINGS = 1; // Request code for settings activity
    public static final int REQUEST_LOGOUT = 2; // Request code for logout action

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        // Retrieve the intent that started this activity
        Intent lastIntent = getIntent();

        // Retrieve the token value from the intent
        token = lastIntent.getStringExtra("token");
        userName = lastIntent.getStringExtra("username");
        currentUser = (User) lastIntent.getSerializableExtra("user");
        fbToken = lastIntent.getStringExtra("FBtoken");
        if (currentUser == null) {
            userApi = new UsersApi(ServerIP);
            try {
                userApi.GetMyDetails(userName, token, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            currentUser = response.body();
                        } else {
                            showCustomToast("Error from the server");
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        showCustomToast("Invalid server call!");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //Adding the Image of the active user
        ImageView ivUserProfilePic = findViewById(R.id.ivUserProfilePic);
        ivUserProfilePic.setImageBitmap(base64ToBitmap(currentUser.getProfilePic()));

        lstFeed = findViewById(R.id.lstContacts);
        chatsApi = new ChatsApi(ServerIP);
//        chatsApi.GetMyChats(token, new Callback<List<Chat>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
//                if (response.isSuccessful()) {
//                    List<Chat> chats = response.body();
//                    contactList = chats;
//                    final ContactAdapter feedAdapter = new ContactAdapter(contactList, ContactsView.this, userName,token);
//                    lstFeed.setAdapter(feedAdapter);
//                } else {
//                    // Handle unsuccessful response
//                    System.out.println("naor get my chats unsuccessful getmyChats");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Chat>> call, Throwable t) {
//                System.out.println("naor failed to get my chats getmyChats");
//                // Handle failure
//            }
//        });


        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);

        lstFeed = findViewById(R.id.lstContacts);
        chatsApi = new ChatsApi(ServerIP);


        //Thread that activates a function that takes all the user chats
        //and put inside the chatDao
        updateThread = new Thread(() -> {
            db.chatDao().deleteAllChats();
            if (contactList != null) {
                for (Chat chat : contactList) {
                    db.chatDao().insert(chat);
                }
                Collections.sort(contactList, new ChatComparator());
            }
        });
        //Getting all the chats of the user into ChatDao
        chatsApi.GetMyChats(token, new Callback<List<Chat>>() {

            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    contactList = response.body();
                    updateThread.start();
                } else {
                    // Handle unsuccessful response
                    showCustomToast("Error from the server");
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                showCustomToast("Invalid server call!");
                finish();
            }

        });
        try {
            updateThread.join();
        } catch (Exception e){
            e.printStackTrace();
        }

        //After click, getting all the messages for the chat
        lstFeed.setOnItemClickListener((adapterView, view, i, l) -> {
            int chatId = contactList.get(i).getId();

            chatsApi.GetMessagesByChatId(token, chatId, (new Callback<List<Message>>() {

                @Override
                public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                    if (response.isSuccessful()) {
                        msg = response.body();
                        if (msg != null) {
                            handleResponse(msg, chatId);
                        }

                    } else {
                        // Handle unsuccessful response
                        showCustomToast("Error from the server");
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    showCustomToast("Invalid server call!");
                    finish();
                }
            }));
        });

        // Open settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsView.this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_SETTINGS);
            }
        });

        //Open add intent
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddChatActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(() -> {
            //Getting all the chats in the dao
            List<Chat> testList = db.chatDao().index();
            contactList = new ArrayList<>();
            for (Chat chat : testList) {
                //Checking if its a chat of active user
                String u1 = chat.getUsers().get(0).getUsername();
                String u2 = chat.getUsers().get(1).getUsername();
                if (u1.equals(userName) || u2.equals(userName)) {
                    contactList.add(chat);
                }
            }
            Collections.sort(contactList, new ChatComparator());
            runOnUiThread(() -> {
                final ContactAdapter ContactAdapter = new ContactAdapter(contactList, ContactsView.this, userName, token);
                lstFeed.setAdapter(ContactAdapter);
            });
        }).start();
        // Retrieve the intent that started this activity
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTINGS && resultCode == RESULT_OK) {
            int logOut = data.getIntExtra("logOut", 0);
            if (logOut == REQUEST_LOGOUT) {
                // User has successfully logged out
                finish();
            }
        }
    }

    public void handleResponse(List<Message> msgList, int chatId) {
//        List<String> list = new ArrayList<>();
//        for (Message m : msgList) {
//            list.add(m.getContent());
//        }
        String otherUserName = getOtherDisplayName(chatId);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("list", (ArrayList<Message>) msgList);
        intent.putExtra("user", (User) currentUser);
        intent.putExtra("token", (String) token);
        intent.putExtra("chatId", (int) chatId);
        intent.putExtra("otherUserName", otherUserName);
        startActivity(intent);
    }

    public Chat getChatById(int chatId){
        for(Chat c : contactList){
            if(c.getId() == chatId){
                return c;
            }
        }
        return null;
    }


    public String getOtherDisplayName(int chatId) {
        Chat chat = getChatById(chatId);
       if(chat.getUsers().get(0).getUsername().equals(userName)){
           return chat.getUsers().get(1).getDisplayName();
       }
       else{
          return chat.getUsers().get(0).getDisplayName();
       }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //log out the user.
        FBTokenApi fbTokenApi = new FBTokenApi();
        fbTokenApi.logOutMyUser(currentUser.getUsername(),fbToken, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }
                else{
                    showCustomToast("Didnt log out successfully");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showCustomToast("Error from the server");
            }
        });
    }


    public Bitmap base64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_warning,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 32);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}

