package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;

public class ChatComposeActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "ChatComposeActivity";
    private Context mContext = ChatComposeActivity.this;

    // VIEWS
    private RecyclerView messagesRecyclerView;
    private EditText messageInput;
    private Button sendMessageBtn;

    // VARIABLES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_compose);

        Log.d(TAG, "onCreate: Running...");

        // INITIALIZING VIEWS
        messageInput = findViewById(R.id.activity_chat_message_input);
        messagesRecyclerView = findViewById(R.id.activity_chat_messages);
        sendMessageBtn = findViewById(R.id.activity_chat_send_button);

        messagesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setStackFromEnd(true);
    }

    private void sendMessage(View view) {
        final String MESSAGE_VALUE = messageInput.getText().toString().trim();
        if(!MESSAGE_VALUE.isEmpty())
        {

        }
        else
        {
            Toast.makeText(mContext, "Messages can't be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
