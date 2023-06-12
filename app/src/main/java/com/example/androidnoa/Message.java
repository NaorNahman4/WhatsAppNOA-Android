package com.example.androidnoa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private Date created;
    private User sender;
    private String Content;

    //Constructor
    public Message(Date created, User sender, String content) {
        this.created = created;
        this.sender = sender;
        Content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
