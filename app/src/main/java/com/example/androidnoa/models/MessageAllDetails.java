package com.example.androidnoa.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidnoa.User;

@Entity
public class MessageAllDetails {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String created;
    private User sender;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageAllDetails(String created, User sender, String content) {
        this.created = created;
        this.sender = sender;
        this.content = content;
    }
}
