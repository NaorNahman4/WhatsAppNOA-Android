package com.example.androidnoa.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidnoa.Message;
import com.example.androidnoa.R;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.models.MessageAllDetails;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> messages;
    private User currentUser; // Add currentUser field


    public ChatAdapter(List<Message> messages, User currentUser) { // Update the constructor
        this.messages = messages;
        this.currentUser = currentUser; // Set the currentUser field
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_details, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSender;
        private TextView textViewContent;
        private TextView textViewCreated;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textViewSender = itemView.findViewById(R.id.textViewSender);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewCreated = itemView.findViewById(R.id.textViewCreated);
        }

        public void bind(Message message) {
            User sender = message.getSender();
            String username = sender.getUsername();
            textViewContent.setText(message.getContent());
            textViewCreated.setText(message.getCreated());

            // Check if the message is from the current user
            if (sender.getUsername().equals(currentUser.getUsername())) {
                // Set layout params for the current user's messages to align to the right
                textViewContent.setBackgroundResource(R.drawable.current_user_message_background);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                layoutParams.setMarginStart(30); // Adjust the margin as per your preference
                itemView.setLayoutParams(layoutParams);
            } else {
                // Set layout params for the other user's messages to align to the right
                textViewContent.setBackgroundResource(R.drawable.other_user_message_background);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                layoutParams.setMarginStart(700); // Adjust the margin as per your preference
                itemView.setLayoutParams(layoutParams);
            }
        }


    }
}
