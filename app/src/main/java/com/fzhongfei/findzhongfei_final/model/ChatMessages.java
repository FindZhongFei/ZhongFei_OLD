package com.fzhongfei.findzhongfei_final.model;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ChatMessages implements Serializable {

    private String everyMessageId, everyMessageTarget, everyMessageContent, everyMessageCreatedAt, everyMessageStatus,
                   everyMessageError, everyMessageErrorMessage, everyMessageType, everyMessageToken;
    private ChatUser mUserChat;

    public ChatMessages() {
    }

    public ChatMessages(String everyMessageId, String everyMessageTarget, String everyMessageContent, String everyMessageCreatedAt, ChatUser userChat) {
        this.everyMessageId = everyMessageId;
        this.everyMessageTarget = everyMessageTarget;
        this.everyMessageContent = everyMessageContent;
        this.everyMessageCreatedAt = everyMessageCreatedAt;
//        this.eachMessageCreatedAt = String.valueOf(new Date().getTime());
        this.mUserChat = userChat;
    }

    public String getEveryMessageId() {
        return everyMessageId;
    }
    public void setEveryMessageId(String everyMessageId) {
        this.everyMessageId = everyMessageId;
    }

    public String getEveryMessageTarget() {
        return everyMessageTarget;
    }
    public void setEveryMessageTarget(String everyMessageTarget) {
        this.everyMessageTarget = everyMessageTarget;
    }

    public String getEveryMessageContent() {
        return everyMessageContent;
    }
    public void setEveryMessageContent(String everyMessageContent) {
        this.everyMessageContent = everyMessageContent;
    }

    public String getEveryMessageCreatedAt() {
        return everyMessageCreatedAt;
    }
    public void setEveryMessageCreatedAt(String everyMessageCreatedAt) {
        this.everyMessageCreatedAt = everyMessageCreatedAt;
    }

    public ChatUser getUserChat() {
        return mUserChat;
    }
    public void setUserChat(ChatUser userChat) {
        this.mUserChat = userChat;
    }
}
