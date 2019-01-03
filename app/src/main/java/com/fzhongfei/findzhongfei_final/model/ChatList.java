package com.fzhongfei.findzhongfei_final.model;

import java.io.Serializable;

public class ChatList implements Serializable {
    private String messageId, senderName, lastMessage, messageTime, messageToken, messageStatus;
    private int unreadCount;

    public ChatList() {
    }

    public ChatList(String senderName, String messageId, String lastMessage, String messageTime,
                    String messageToken, String messageStatus, int unreadCount) {
        this.senderName = senderName;
        this.messageId = messageId;
        this.lastMessage = lastMessage;
        this.messageTime = messageTime;
        this.messageToken = messageToken;
        this.messageStatus = messageStatus;
        this.unreadCount = unreadCount;
    }

    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageToken() {
        return messageToken;
    }
    public void setMessageToken(String messageToken) {
        this.messageToken = messageToken;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public int getUnreadCount() {
        return unreadCount;
    }
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}