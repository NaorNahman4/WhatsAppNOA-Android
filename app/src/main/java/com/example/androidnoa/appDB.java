package com.example.androidnoa;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Chat.class, Message.class}, version = 1)
public abstract  class appDB extends RoomDatabase {
    public abstract UserDao userDao();

}
