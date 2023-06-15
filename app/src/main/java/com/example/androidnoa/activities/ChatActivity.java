package com.example.androidnoa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidnoa.adapters.ChatAdapter;
import com.example.androidnoa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<String> messages;

    private EditText editTextMessage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_acitivity);

        Intent intent = getIntent();
        String displayName = "test";
        TextView textViewDisplayName = findViewById(R.id.textViewUsername);
        textViewDisplayName.setText(displayName);
        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messages = new ArrayList<>();
        messages = intent.getStringArrayListExtra("list");
        chatAdapter = new ChatAdapter(messages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(chatAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            messages.add(messageText);
            Collections.reverse(messages); // Reverse the order of messages
            chatAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
            editTextMessage.setText("");
        }
    }
}
