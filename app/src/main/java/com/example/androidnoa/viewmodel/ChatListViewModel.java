package com.example.androidnoa.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.androidnoa.Chat;

public class ChatListViewModel {
    private MutableLiveData<Chat> chatList;

    public MutableLiveData<Chat> getList(){
        if(chatList == null){
            chatList = new MutableLiveData<>();
        }
        return chatList;
    }
}
