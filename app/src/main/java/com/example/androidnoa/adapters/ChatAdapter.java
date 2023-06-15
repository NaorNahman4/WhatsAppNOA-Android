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
import com.example.androidnoa.models.MessageAllDetails;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> messages;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
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
            textViewSender.setText(message.getSender().getUsername());
            textViewContent.setText(message.getContent());
            textViewCreated.setText(message.getCreated());
        }
    }
}
