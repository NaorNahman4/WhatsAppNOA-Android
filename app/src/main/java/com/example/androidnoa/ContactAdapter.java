package com.example.androidnoa;

import static com.example.androidnoa.activities.loginActivity.ServerIP;
import static com.example.androidnoa.activities.loginActivity.db;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidnoa.api.ChatsApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdapter extends BaseAdapter {

    private Thread t;
    List<Chat> contactList;

    Activity prevActivity;
    String user;
    String token;

    private class ViewHolder {
        int id;
        TextView displayName;
        TextView lastMessageDate;
        TextView lastMessageContent;
        ImageView profilePic;
    }

    //Constructor
    public ContactAdapter(List<Chat> contactList, Activity prevActivity, String user, String token){
        this.contactList = contactList;
        this.prevActivity = prevActivity;
        this.user = user;
        this.token = token;
    }

    @Override
    public int  getCount(){
        return this.contactList.size();
    }

    @Override
    public Chat getItem(int id){
        for (Chat contact : this.contactList){
            if(contact.id == id){
                return contact;
            }
        }
        return null;
    }

    //So the adapter wont be abstract
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.displayName = convertView.findViewById(R.id.display_name);
            viewHolder.id = 1; //Fictive id
            viewHolder.profilePic = convertView.findViewById(R.id.Profile_Pic);
            viewHolder.lastMessageContent = convertView.findViewById(R.id.last_message_content);
            viewHolder.lastMessageDate = convertView.findViewById(R.id.last_message_date);


            convertView.setTag(viewHolder);

            Button btnDel = convertView.findViewById(R.id.btnDelete);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatsApi chatsApi = new ChatsApi(ServerIP);
                    chatsApi.deleteMyChat(token ,contactList.get(position).getId(),
                            new Callback<ResponseBody>() {

                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    //on success
                                    if(response.isSuccessful()){
                                        //toast message success
                                        Toast.makeText(prevActivity, "Chat deleted", Toast.LENGTH_SHORT).show();
                                        //Create thread for the deleting
                                        t = new Thread(() -> {
                                            Chat toDelete = contactList.get(position);
                                            db.chatDao().delete(toDelete);
                                            prevActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    contactList.remove(position);
                                                    notifyDataSetChanged();
                                                }
                                            });
                                            //Need to put live data
                                        });
                                        t.start();
                                    }
                                    else{
                                        //toast message fail
                                        Toast.makeText(prevActivity, "Chat not deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    //toast message fail
                                    Toast.makeText(prevActivity, "Chat not deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                    try{
                        t.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
        Chat chat = this.contactList.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        String displayName = chat.getUsers().get(0).getDisplayName();
        if (chat.getUsers().get(0).getUsername().equals(user)){
            displayName = chat.getUsers().get(1).getDisplayName();
        }
        viewHolder.profilePic.setImageBitmap(getOtherProfilePicBitmap(chat,user));
//        if (viewHolder.profilePic.getDrawable()==null){
//            viewHolder.profilePic.setImageResource(R.drawable.default_pic);
//        }
        viewHolder.displayName.setText(displayName);

       // viewHolder.profilePic.setImageResource(chat.getUsers().get(0).getProfilePic());
        String lastMessage = chat.getLastMessageContent();
        if(lastMessage.length() > 12){
            lastMessage = lastMessage.substring(0,12) + "...";
        }
        viewHolder.lastMessageContent.setText(lastMessage);
        viewHolder.lastMessageDate.setText(chat.getLastMessageDate());
        return convertView;
    }

    private Bitmap getOtherProfilePicBitmap(Chat chat, String username) {
        if (chat.users.get(0).getUsername().equals(username)) {
            String base64String = chat.users.get(1).getProfilePic();
            return base64ToBitmap(base64String);
        } else {
            String base64String = chat.users.get(0).getProfilePic();
            return base64ToBitmap(base64String);
        }
    }
    public Bitmap base64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}










