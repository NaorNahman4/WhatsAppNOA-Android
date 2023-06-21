package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidnoa.Message;
import com.example.androidnoa.User;
import com.example.androidnoa.adapters.ChatAdapter;
import com.example.androidnoa.R;
import com.example.androidnoa.api.ChatsApi;
import com.example.androidnoa.models.MessageAllDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messages;

    private EditText editTextMessage;
    private Button buttonSend;
    private User currentUser;
    private String token;
    private int chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_acitivity);


        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");
        token = intent.getStringExtra("token");
        chatId = intent.getIntExtra("chatId", 0);
        String displayName = intent.getStringExtra("otherUserName");
        TextView textViewDisplayName = findViewById(R.id.textViewUsername);
        textViewDisplayName.setText(displayName);
        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        messages = new ArrayList<>();
        messages = (ArrayList<Message>) intent.getSerializableExtra("list");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public void scrollToPositionWithOffset(int position, int offset) {
                super.scrollToPositionWithOffset(position, -(recyclerView.getHeight() - offset));
            }
        };

        chatAdapter = new ChatAdapter(messages, currentUser);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);

        recyclerView.scrollToPosition(messages.size() - 1);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        editTextMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    sendMessage(); // Call the sendMessage() method when the Enter key is pressed
                    return true; // Consume the event
                }
                return false;
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // post request to the server to send the message
            ChatsApi chatsApi = new ChatsApi();
            String created = getCurrentDateTime(); // Get the current date and time
            User sender = currentUser; // Get the current user
            Message messageSend = new Message(created, sender, messageText);
            chatsApi.sendMessage(token, chatId, messageText, (new Callback<Message>() {
                @Override
                public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                    if (response.isSuccessful()) {
                        Message message = response.body();
                        System.out.println("naor sent message : " + message);
                    } else {
                        // Handle unsuccessful response
                        System.out.println("Cant get messages- unsuccesfull");
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    System.out.println("naor failed to get my chats");
                    // Handle failure
                }
            }));
            messages.add(messageSend);
            Thread t = new Thread(() -> {
                db.chatDao().updateChatMessages(chatId, messages);
            });
            t.start();
            try {
                t.join();
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messages.size() - 1);
                editTextMessage.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        // Specify the date and time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy", Locale.getDefault());
        // Format the date and time
        return dateFormat.format(calendar.getTime());
    }


}
