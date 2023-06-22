package com.example.androidnoa;

import static com.example.androidnoa.activities.loginActivity.ServerIP;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnoa.activities.ChatActivity;

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
            System.out.println("On Message triggered");
            // Extract the message data
            String content = remoteMessage.getData().get("content");
            String senderUsername = remoteMessage.getData().get("senderUsername");
            System.out.println(content);
            System.out.println(senderUsername);
            // Display a notification or perform custom actions
            showNotification(senderUsername, content);
            ChatsApi chatsApi = new ChatsApi(ServerIP);
            chatsApi.GetMyChats(ChatActivity.instance.getToken(), new Callback<List<Chat>>() {
                @Override
                public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                    if (response.isSuccessful()) {
                        List<Chat> chats = response.body();
                        System.out.println("On Message triggered Good!!!!");
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

    private void showNotification(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel
            CharSequence channelName = "Channel Name";
            String channelDescription = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("channel_id", channelName, importance);
            channel.setDescription(channelDescription);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.bubble_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
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
