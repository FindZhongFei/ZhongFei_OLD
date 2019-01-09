package com.fzhongfei.findzhongfei_final.model;

import java.io.Serializable;

public class ChatList implements Serializable {
    private String partnerToken, partnerName, partnerEmail, partnerProfile, chatId, messageToken, lastMessage, messageTime, messageStatus;
    private int unreadCount;

    public ChatList() {
    }

    public ChatList(String partnerToken, String partnerName, String partnerEmail, String chatId, String messageToken, String lastMessage, String messageTime,
                    String messageStatus, int unreadCount) {
        this.partnerToken = partnerToken;
        this.partnerName = partnerName;
        this.partnerEmail = partnerEmail;
        this.chatId = chatId;
        this.messageToken = messageToken;
        this.lastMessage = lastMessage;
        this.messageTime = messageTime;
        this.messageStatus = messageStatus;
        this.unreadCount = unreadCount;
    }


    public String getPartnerToken() {
        return partnerToken;
    }
    public void setPartnerToken(String partnerToken) {
        this.partnerToken = partnerToken;
    }

    public String getPartnerName() {
        return partnerName;
    }
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }
    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageToken() {
        return messageToken;
    }
    public void setMessageToken(String messageToken) {
        this.messageToken = messageToken;
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