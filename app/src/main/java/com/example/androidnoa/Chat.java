package com.example.androidnoa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidnoa.models.UserAllDetails;
import com.example.androidnoa.models.UserNameAndPass;

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
    public String getOtherDisplayName(String my_name) {
        if (users.get(0).getUsername().equals(my_name)) {
            return users.get(1).getUsername();
        } else {
            return users.get(0).getUsername();
        }
    }
    public String getLastMessageContent(){
        if (this.messages == null || this.messages.size() == 0){
            return "";
        }
        return this.messages.get(messages.size() - 1).getContent();
    }
    public String getLastMessageDate(){
        if (this.messages == null || this.messages.size() == 0){
            return "";
        }
        return this.messages.get(messages.size() - 1).getCreated();
    }

}
