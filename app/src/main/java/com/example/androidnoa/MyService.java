package com.example.androidnoa;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidnoa.activities.loginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    private int notificationId = 1;

    public MyService() {
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "Messages", importance);
            channel.setDescription("Default channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Handle the received FCM message
        if (remoteMessage.getData().size() > 0) {
            // Extract the message data
            String content = remoteMessage.getData().get("content");
            String senderUsername = remoteMessage.getData().get("senderUsername");
            System.out.println("naor content: " + content);
            System.out.println("naor senderUsername: " + senderUsername);

            // Display a notification or perform custom actions
            showNotification(content, senderUsername);
        }
    }

    private void showNotification(String message, String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Message from " + username);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private int generateNotificationId() {
        // Generate a unique notification ID using a timestamp or other logic
        // For simplicity, you can use a random number generator as well
        return (int) System.currentTimeMillis();
    }
}
