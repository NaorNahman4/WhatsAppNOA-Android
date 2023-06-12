package com.example.androidnoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    List<Chat> contactList;

    Context prevActivity;

    private class ViewHolder {
        int id;
        TextView displayName;
        TextView lastMessageDate;
        TextView lastMessageContent;
        ImageView profilePic;
    }

    //Constructor
    public ContactAdapter(List<Chat> contactList, Context prevActivity) {
        this.contactList = contactList;
        this.prevActivity = prevActivity;
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
        }

        Chat chat = this.contactList.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.displayName.setText(chat.getUsers().get(0).getDisplayName());
        viewHolder.profilePic.setImageResource(chat.getUsers().get(0).getProfilePic());
        viewHolder.lastMessageContent.setText(chat.getFictiveLastMessage());
        viewHolder.lastMessageDate.setText(chat.getFictiveLastMessageDate());

        return convertView;
    }
}
