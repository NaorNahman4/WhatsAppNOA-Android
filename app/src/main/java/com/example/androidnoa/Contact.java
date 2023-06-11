package com.example.androidnoa;

public class Contact {
    int id;
    String displayName;
    String lastMessageDate;
    String lastMessageContent;
    int profilePic;

    //Constructor without image
    public Contact(int id, String displayName, String lastMessageDate, String lastMessageContent) {
        this.id = id;
        this.displayName = displayName;
        this.lastMessageDate = lastMessageDate;
        this.lastMessageContent = lastMessageContent;
    }

    //Constructor with image
    public Contact(int id, String displayName, String lastMessageDate, String lastMessageContent, int profilePic) {
        this(id, displayName, lastMessageDate, lastMessageContent);
        this.profilePic = profilePic;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public int getProfilePic() {
        return profilePic;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
