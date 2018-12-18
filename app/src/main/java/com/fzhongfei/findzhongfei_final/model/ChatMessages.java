package com.fzhongfei.findzhongfei_final.model;

import java.io.Serializable;

public class ChatMessages implements Serializable {
    private String id, message, createdAt;
    private ChatUserChat mUserChat;

    public ChatMessages() {
    }

    public ChatMessages(String id, String message, String createdAt, ChatUserChat userChat) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.mUserChat = userChat;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ChatUserChat getUserChat() {
        return mUserChat;
    }
    public void setUserChat(ChatUserChat userChat) {
        this.mUserChat = userChat;
    }
}
