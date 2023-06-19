package com.example.androidnoa;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
       int a=1;
    }

}