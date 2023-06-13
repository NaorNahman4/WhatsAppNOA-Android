package com.example.androidnoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowAllUsersActivity extends AppCompatActivity {
    private appDB db;
    private UserDao userDao;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        try {
            db = Room.databaseBuilder(getApplicationContext(),
                            appDB.class, "Users")
                    .build();
            userDao = db.userDao();
                users = userDao.index();
            if(users != null) {
                ListView lvUsers = findViewById(R.id.lvUsers);
                ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);

                lvUsers.setAdapter(adapter);
            }
        } catch (Exception e) {

        }
    }
}