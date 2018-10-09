package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fzhongfei.findzhongfei_final.R;

public class RegisterOrLoginPopupActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterOrLoginPopupAct";
    private Context mContext = RegisterOrLoginPopupActivity.this;

    // VIEWS
    Button registerButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_register_or_login);

        Log.d(TAG, "onCreate: Running...");

        loginButton = findViewById(R.id.popup_login_button);
        registerButton = findViewById(R.id.popup_register_button);

        handleClicks();
    }

    public void handleClicks() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
