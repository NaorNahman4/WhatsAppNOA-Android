package com.example.androidnoa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate=true)
    int id;
    ArrayList<User> users;

    ArrayList<Message> messages;

    public Chat(ArrayList<User> users) {
        this.users = users;
        this.messages = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getFictiveLastMessage(){
        return "Last Message";
    }

    public String getFictiveLastMessageDate(){
        return "Last Date";
    }
}
