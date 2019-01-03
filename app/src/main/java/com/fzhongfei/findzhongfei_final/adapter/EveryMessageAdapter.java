package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.ChatMessages;
import com.fzhongfei.findzhongfei_final.model.UserProfile;

import java.util.List;

public class EveryMessageAdapter extends RecyclerView.Adapter {

    private static final String TAG = "EveryMessageAdapter";
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<ChatMessages> mMessageList;

    public EveryMessageAdapter(Context context, List<ChatMessages> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatMessages message = mMessageList.get(position);
        UserProfile mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        if(message.getUserChat().getToken().equals(mUserProfile.getUserToken()))
        {
            // If the current user is the sender of the message
             return VIEW_TYPE_MESSAGE_SENT;
        }
        else
        {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessages message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(ChatMessages message) {
            messageText.setText(message.getEachMessageContent());

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(DateUtils.formatDateTime(mContext, Long.valueOf(message.getEachMessageCreatedAt()), 0));
//            timeText.setText(message.getEachMessageCreatedAt());
            timeText.setText(DateFormat.format("HH:mm", Long.valueOf(message.getEachMessageCreatedAt())));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message_receiver_content);
            timeText = itemView.findViewById(R.id.received_text_message_time);
            nameText = itemView.findViewById(R.id.message_receiver_name);
            profileImage = itemView.findViewById(R.id.received_message_image);
        }

        void bind(ChatMessages message) {
            messageText.setText(message.getEachMessageContent());

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(DateUtils.formatDateTime(mContext, Long.valueOf(message.getEachMessageCreatedAt()), 0));
            timeText.setText(message.getEachMessageCreatedAt());
//            timeText.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", Long.valueOf(message.getEachMessageCreatedAt())));
//            timeText.setText(DateFormat.format("HH:mm", Long.valueOf(message.getEachMessageCreatedAt())));

            nameText.setText(message.getUserChat().getName());

            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
}
