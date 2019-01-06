package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.EveryMessageAdapter;
import com.fzhongfei.findzhongfei_final.model.ChatList;
import com.fzhongfei.findzhongfei_final.model.ChatMessages;
import com.fzhongfei.findzhongfei_final.model.ChatUser;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.FetchedMessages;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatComposeActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "ChatComposeActivity";
    private Context mContext = ChatComposeActivity.this;

    // VIEWS
    private RecyclerView messagesRecyclerView;
    private EveryMessageAdapter mMessageAdapter;
    public static List<ChatMessages> mMessagesList = new ArrayList<>();
    public static List<ChatMessages> mMessagesListTemp = new ArrayList<>();
    private EditText messageInput;
    private Button sendMessageBtn;
    private String receiverName, companyToken;

    // VARIABLES
    private UserProfile mUserProfile;
    private CompanyProfile mCompanyProfile;

    SharedPreferences userSharedPreferences;
    SharedPreferences companySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_compose);
        String targetPartnerToken = getIntent().getStringExtra("partnerToken");
//        ArrayList mCounters = new ArrayList();
        int mCounter  =0 ;
        mMessagesList.clear();
        Log.d(TAG, "onCreate: the size of temp is "+mMessagesListTemp.size());
        for(int i=0; i<mMessagesListTemp.size(); i++)
        {
            String partnerToken = mMessagesListTemp.get(i).getUserChat().getToken();
            if(partnerToken.equals(targetPartnerToken))
            {
                ChatUser partnerInfo = mMessagesListTemp.get(i).getUserChat();
                String messageContent = mMessagesListTemp.get(i).getEachMessageContent();
                Log.d(TAG, "onCreate: CHAT"+i+": "+partnerInfo.getName()+" --> "+messageContent);
                mMessagesList.add(mCounter, mMessagesListTemp.get(i));
                mCounter++;
            }
//            ChatUser partnerInfo = mMessagesListTemp.get(i).getUserChat();
//            String messageContent = mMessagesListTemp.get(i).getEachMessageContent();
//            Log.d(TAG, "onCreate: CHAT"+i+": "+partnerInfo.getName()+" --> "+messageContent);
        }
        Log.d(TAG, "onCreate: Running...");
        receiverName = getIntent().getStringExtra("receiverName");
        companyToken = getIntent().getStringExtra("CompanyToken");

        setUpActivityToolbar();

        userSharedPreferences = getSharedPreferences("userPreference", 0);
        companySharedPreferences= getSharedPreferences("companyPreference", 0);

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);
        mCompanyProfile = new CompanyProfile(mContext);
        mCompanyProfile.setPropertiesFromSharePreference(mContext);

        // INITIALIZING VIEWS
        messageInput = findViewById(R.id.activity_chat_message_input);
        messagesRecyclerView = findViewById(R.id.activity_chat_messages);
        mMessageAdapter = new EveryMessageAdapter(mContext, mMessagesList);
        sendMessageBtn = findViewById(R.id.activity_chat_send_button);

        messagesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        messagesRecyclerView.setAdapter(mMessageAdapter);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // UI - SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;
        Window window;
        GradientDrawable mGradientDrawable;

        mGradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{0xFF5258A6, 0xFF7375B7});

        // CHANGE THE STATUS BAR COLOR TO TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(mContext.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(mContext.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(mGradientDrawable);
        }

        mToolbar = findViewById(R.id.chat_compose_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setTitleMarginStart(-70);
        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(receiverName);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void sendMessage() {
        final String MESSAGE_VALUE = messageInput.getText().toString().trim();
        final long CURRENT_TIME_IN_MILLISECONDS = System.currentTimeMillis() / 1000;
        final String CURRENT_TIME_IN_SECONDS = String.valueOf(CURRENT_TIME_IN_MILLISECONDS);
        final String targetToken = companyToken;;

        customStringRequest registerRequest = new customStringRequest("message/communicate.php");
        callBackImplement callBack = new callBackImplement(mContext);

        HashMap<String, String> Params = new HashMap<>();

        if(!MESSAGE_VALUE.isEmpty())
        {
            ChatMessages chatMessages = new ChatMessages("ID",
                    MESSAGE_VALUE, String.valueOf(new Date().getTime()),
                    new ChatUser(mUserProfile.getUserToken(), mUserProfile.getUserFirstName(),
                            mUserProfile.getUserEmail()));
            mMessagesList.add(0, chatMessages);
            mMessageAdapter = new EveryMessageAdapter(mContext, mMessagesList);
            messagesRecyclerView.setAdapter(mMessageAdapter);

            if(userSharedPreferences.contains("userIsLoggedIn"))
            {
                String hostToken = mUserProfile.getUserToken();

                Params.put("hostType", "user");
                Params.put("targetType", "company");
                Params.put("hostToken", hostToken);
                callBack.SetRequestType("customerSendingMessage");
            }
            else if(companySharedPreferences.contains("companyIsLoggedIn"))
            {
                String hostToken = mCompanyProfile.getCompanyToken();

                Params.put("hostType", "company");
                Params.put("targetType", "user");
                Params.put("hostToken", hostToken);
                callBack.SetRequestType("companySendingMessage");
            }
            else
            {
                Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, UserSignedInActivity.class));
            }

            Params.put("targetToken", targetToken);
            Params.put("plainText", MESSAGE_VALUE);
            Params.put("timeInSec", CURRENT_TIME_IN_SECONDS);

            registerRequest.setParams(Params);
            callBack.setParams(Params);
            registerRequest.startConnection(mContext, callBack, Params);

            messageInput.getText().clear();
        }
        else
        {
            Toast.makeText(mContext, "Messages can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setMessageContent(String messageText) {
            TextView myMessage = mView.findViewById(R.id.layout_single_message_sent);
            myMessage.setText(messageText);
        }
    }
}
