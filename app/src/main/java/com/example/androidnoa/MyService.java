package com.example.androidnoa;

import static com.example.androidnoa.activities.loginActivity.ServerIP;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnoa.activities.ChatActivity;
import com.example.androidnoa.activities.ContactsView;
import com.example.androidnoa.activities.loginActivity;
import com.example.androidnoa.adapters.ChatAdapter;
import com.example.androidnoa.api.ChatsApi;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            // Display a notification or perform custom actions
            showNotification(content, senderUsername, remoteMessage);
            ChatsApi chatsApi = new ChatsApi(ServerIP);
            chatsApi.GetMyChats(ChatActivity.instance.getToken(), new Callback<List<Chat>>() {
                @Override
                public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                    if (response.isSuccessful()) {
                        List<Chat> chats = response.body();
                        for(Chat chat : chats){
                            //Getting the specific chat we want to update
                            if(chat.getUsers().get(0).getUsername().equals(senderUsername)
                            || chat.getUsers().get(1).getUsername().equals(senderUsername)){
                                ChatActivity.instance.setMessages(chat.messages);
                                setAdapterForMessages(ChatActivity.instance.getMessages());
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Chat>> call, Throwable t) {
                    System.out.println("naor failed to get my chats getmyChats");
                    // Handle failure
                }
            });
        }
    }

    private void showNotification(String content, String senderUsername, RemoteMessage remoteMessage) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, loginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (remoteMessage.getNotification() != null) {
            builder.setContentTitle(remoteMessage.getNotification().getTitle());
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        } else {
            // If the notification is not available, use the data payload for the title and body
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            builder.setContentTitle(title);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("naor no permission");
            return;
        }
        notificationManager.notify(notificationId, builder.build());
        ++notificationId;
    }

    private int generateNotificationId() {
        // Generate a unique notification ID using a timestamp or other logic
        // For simplicity, you can use a random number generator as well
        return (int) System.currentTimeMillis();
    }

    private void setAdapterForMessages(List<Message> messages){
        ChatAdapter chatAdapter = ChatActivity.instance.getChatAdapter();
        RecyclerView recyclerView = ChatActivity.instance.getRecyclerView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public void scrollToPositionWithOffset(int position, int offset) {
                super.scrollToPositionWithOffset(position, -(recyclerView.getHeight() - offset));
            }
        };

        chatAdapter = new ChatAdapter(messages, ChatActivity.instance.getCurrentUser());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);

        recyclerView.scrollToPosition(messages.size() - 1);
    }
}
