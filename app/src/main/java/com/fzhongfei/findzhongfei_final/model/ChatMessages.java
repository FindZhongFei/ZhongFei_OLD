package com.fzhongfei.findzhongfei_final.model;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ChatMessages implements Serializable {

    private String eachMessageId, eachMessageContent, eachMessageCreatedAt;
    private ChatUser mUserChat;

    public ChatMessages() {
    }

    public ChatMessages(String eachMessageId, String eachMessageContent, String eachMessageCreatedAt, ChatUser userChat) {
        this.eachMessageId = eachMessageId;
        this.eachMessageContent = eachMessageContent;
        this.eachMessageCreatedAt = eachMessageCreatedAt;
//        this.eachMessageCreatedAt = String.valueOf(new Date().getTime());
        this.mUserChat = userChat;
    }

    public String getEachMessageId() {
        return eachMessageId;
    }
    public void setEachMessageId(String eachMessageId) {
        this.eachMessageId = eachMessageId;
    }

    public String getEachMessageContent() {
        return eachMessageContent;
    }
    public void setEachMessageContent(String eachMessageContent) {
        this.eachMessageContent = eachMessageContent;
    }

    public String getEachMessageCreatedAt() {
        return eachMessageCreatedAt;
    }
    public void setEachMessageCreatedAt(String eachMessageCreatedAt) {
        this.eachMessageCreatedAt = eachMessageCreatedAt;
    }

    public ChatUser getUserChat() {
        return mUserChat;
    }
    public void setUserChat(ChatUser userChat) {
        this.mUserChat = userChat;
    }
}
