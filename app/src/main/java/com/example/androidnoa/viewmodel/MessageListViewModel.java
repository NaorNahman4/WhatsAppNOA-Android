package com.example.androidnoa.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.androidnoa.Chat;

public class MessageListViewModel {

    private MutableLiveData<Chat> messageList;

    public MutableLiveData<Chat> getList(){
        if(messageList == null){
            messageList = new MutableLiveData<>();
        }
        return messageList;
    }
}
