package com.example.androidnoa;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Chat.class, Message.class}, version = 17)
@TypeConverters(Convertors.class)
public abstract  class appDB extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ChatDao chatDao();

    public abstract MessageDao messageDao();
}
