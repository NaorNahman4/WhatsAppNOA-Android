package com.example.androidnoa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate=true)
    int id;
    List<User> users;

    List<Message> messages;

    public Chat(List<User> users) {
        this.users = users;
        this.messages = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getFictiveLastMessage(){
        return "Last Message";
    }

    public String getFictiveLastMessageDate(){
        return "Last Date";
    }
}
