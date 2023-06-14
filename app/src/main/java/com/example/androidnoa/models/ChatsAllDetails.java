package com.example.androidnoa.models;

import com.example.androidnoa.Message;
import com.example.androidnoa.User;

import java.util.List;

public class ChatsAllDetails {
    private int id;
    List<User> users;

    List<Message> messages;


    public ChatsAllDetails(int id, List<User> users, List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
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
}
