package com.example.androidnoa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM Message")
    List<Message> index();

    @Query("SELECT * FROM Message WHERE id = :id")
    Message getUserById(int id);

    @Insert
    void insert(Message... Message);

    @Update
    void update(Message... Message);

    @Delete
    void delete(Message... Message);

}
