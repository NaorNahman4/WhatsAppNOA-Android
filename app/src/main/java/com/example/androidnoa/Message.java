package com.example.androidnoa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Message implements Serializable {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String created;
    private User sender;
    private String content;

    //Constructor
    public Message(String created, User sender, String content) {
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public String getCreated() {
        return formatDateString(created);
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
        content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String formatDateString(String dateString) {
        //if not in formated
        if(dateString.length() != 19) {
            try {
                // Define input and output date formats
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());

                // Parse the input date string
                Date date = inputFormat.parse(dateString);

                // Format the date to the desired format
                String formattedDate = outputFormat.format(date);

                return formattedDate;
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Handle the error condition appropriately
            }
        }
        else return dateString;
    }
}


