package com.example.androidnoa;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@TypeConverters
public class Convertors {
    //From UserList to string
    @TypeConverter
    public String fromUserList(List<User> users) {
        if (users == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {
        }.getType();
        return gson.toJson(users, type);
    }

    //Back to UserList
    @TypeConverter
    public List<User> toUserList(String userListString) {
        if (userListString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {
        }.getType();
        return gson.fromJson(userListString, type);
    }

    // From User to string
    @TypeConverter
    public String fromUser(User user) {
        if (user == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    // Back to User
    @TypeConverter
    public User toUser(String userString) {
        if (userString == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(userString, User.class);
    }

    //From MessageList to string
    @TypeConverter
    public String fromMessageList(List<Message> messages) {
        if (messages == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {
        }.getType();
        return gson.toJson(messages, type);
    }

    //Back to MessageList
    @TypeConverter
    public List<Message> toMessageList(String messageListString) {
        if (messageListString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {
        }.getType();
        return gson.fromJson(messageListString, type);
    }

    // From Message to string
    @TypeConverter
    public String fromMessage(Message message) {
        if (message == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    // Back to Message
    @TypeConverter
    public Message toMessage(String messageString) {
        if (messageString == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(messageString, Message.class);
    }
}
