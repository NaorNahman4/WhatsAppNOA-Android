package com.example.androidnoa.activities;

import static com.example.androidnoa.activities.loginActivity.db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidnoa.R;
import com.example.androidnoa.User;
import com.example.androidnoa.UserDao;
import com.example.androidnoa.appDB;

import java.util.ArrayList;
import java.util.List;


public class ShowAllUsersActivity extends AppCompatActivity {
    private UserDao userDao;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        try {
            userDao = db.userDao();
            AsyncTask.execute(() -> {
                users = userDao.index();
                if (users != null) {
                    ListView lvUsers = findViewById(R.id.lvUsers);
                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);

                    lvUsers.setAdapter(adapter);
                }
            });

        } catch (Exception e) {

        }
    }
}