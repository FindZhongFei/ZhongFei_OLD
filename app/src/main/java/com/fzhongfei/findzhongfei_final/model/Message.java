package com.fzhongfei.findzhongfei_final.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String id, message, createdAt;
    private UserChat mUserChat;

    public Message() {
    }

    public Message(String id, String message, String createdAt, UserChat userChat) {
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

    public UserChat getUserChat() {
        return mUserChat;
    }
    public void setUserChat(UserChat userChat) {
        this.mUserChat = userChat;
    }
}
