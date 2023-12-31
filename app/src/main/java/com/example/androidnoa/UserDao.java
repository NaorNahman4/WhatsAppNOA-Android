package com.example.androidnoa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> index();

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(int id);

    @Insert
    void insert(User... User);

    @Update
    void update(User... User);


    @Query("DELETE FROM User")
    void deleteAllUsers();
    @Delete
    void delete(User... User);

}
