package com.example.androidnoa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM Chat")
    ArrayList<Chat> index();

    @Query("SELECT * FROM Chat WHERE id = :id")
    Chat getUserById(int id);

    @Insert
    void insert(Chat... Chat);

    @Update
    void update(Chat... Chat);

    @Delete
    void delete(Chat... Chat);
}
