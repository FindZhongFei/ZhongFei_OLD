package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fzhongfei.findzhongfei_final.activity.ChatComposeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchedMessages {

    private static final String TAG = "FetchedMessages";

    private static SharedPreferences sharedPreference;
    private static ArrayList<ChatList> messagesArrayList = new ArrayList<>();
    private static final String NUM_MESSAGES = "num_companies";
    private static final String SHARED_PREFERENCE = "savedMessages";
    private static final String PARTNER_TOKEN = "partnerToken";
    private static final String PARTNER_LOGO_URL = "partnerProfile";
    private static final String PARTNER_NAME = "partnerName";
    private static final String PARTNER_EMAIL = "partnerEmail";
    private static final String CHAT_ID = "id";
    private static final String MESSAGE_STATUS = "messageStatus";
    private static final String MESSAGE_TOKEN = "messageToken";
    private static final String MESSAGE_CONTENT = "messageContent";
    private static final String MESSAGE_TIME = "messageTime";
    private static int UNREAD_COUNT = 0;

    public static void saveNewMessages(Context context, JSONArray chatsWith) throws JSONException {
        sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = sharedPreference.edit();

        ChatList chatList;
        JSONArray everyMessageArray;
        JSONObject retrievedData, messagesObject;
        // TO KNOW HOW MANY MESSAGES ARE SAVED AND ITERATE
        editor.putInt(NUM_MESSAGES, chatsWith.length());

        for(int indexPartners = 0; indexPartners < chatsWith.length(); indexPartners++) {
            retrievedData = chatsWith.getJSONObject(indexPartners);
            everyMessageArray = retrievedData.getJSONArray("messages");

            for(int indexEveryMessage = 0; indexEveryMessage < everyMessageArray.length(); indexEveryMessage++)
            {
                if(everyMessageArray.getJSONObject(indexEveryMessage).getString("messageStatus").equals("sent"))
                {
                    UNREAD_COUNT++;
                }

                // LIST OF PARTNERS
                chatList = new ChatList(
                        retrievedData.getString("partnerToken"),
                        retrievedData.getString("partnerName"),
                        retrievedData.getString("partnerEmail"),
                        retrievedData.getString("id"),
                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageToken"),
                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageContent"),
                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageTime"),
                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageStatus"),
                        UNREAD_COUNT
                );

                editor.putString(PARTNER_TOKEN + "_" + indexEveryMessage, chatList.getPartnerToken());
                editor.putString(PARTNER_NAME + "_" + indexEveryMessage, chatList.getPartnerName());
                editor.putString(PARTNER_EMAIL + "_" + indexEveryMessage, chatList.getPartnerEmail());
                editor.putString(CHAT_ID + "_" + indexEveryMessage, chatList.getChatId());
                editor.putString(MESSAGE_TOKEN + "_" + indexEveryMessage, chatList.getMessageToken());
                editor.putString(MESSAGE_STATUS + "_" + indexEveryMessage, chatList.getMessageStatus());
                editor.putString(MESSAGE_CONTENT + "_" + indexEveryMessage, chatList.getLastMessage());
                editor.putString(MESSAGE_TIME + "_" + indexEveryMessage, chatList.getMessageTime());
                editor.putInt(UNREAD_COUNT + "_" + indexEveryMessage, UNREAD_COUNT);

                editor.apply();
                editor.commit();
//
//                // LIST OF EVERY MESSAGE
//                eachChatMessages = new ChatMessages(
//                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageId"),
//                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageContent"),
//                        everyMessageArray.getJSONObject(indexEveryMessage).getString("messageTime"),
//                        new ChatUser(
//                                retrievedData.getString("partnerToken"),
//                                retrievedData.getString("partnerName"),
//                                "EMAIL"));
            }
        }
    }

    public static ArrayList<ChatList> getSavedMessages(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE, 0);
        ChatList chatList;

        for(int i = 0; i < sharedPreference.getInt(NUM_MESSAGES, 0); i++)
        {
            chatList = new ChatList(
                    sharedPreference.getString(PARTNER_TOKEN + "_" + i, ""),
                    sharedPreference.getString(PARTNER_NAME + "_" + i, ""),
                    sharedPreference.getString(PARTNER_EMAIL + "_" + i, ""),
                    sharedPreference.getString(CHAT_ID + "_" + i, ""),
                    sharedPreference.getString(MESSAGE_TOKEN + "_" + i, ""),
                    sharedPreference.getString(MESSAGE_CONTENT + "_" + i, ""),
                    sharedPreference.getString(MESSAGE_TIME + "_" + i, ""),
                    sharedPreference.getString(MESSAGE_STATUS + "_" + i, ""),
                    sharedPreference.getInt(UNREAD_COUNT + "_" + i, 0)
            );

            messagesArrayList.add(0, chatList);
        }

        return messagesArrayList;
    }
}
