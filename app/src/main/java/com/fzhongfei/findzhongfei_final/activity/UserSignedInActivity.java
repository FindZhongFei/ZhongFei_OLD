package com.fzhongfei.findzhongfei_final.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.utils.DisplayAds;
import com.google.android.gms.ads.AdView;

public class UserSignedInActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserSignedInActivity";
    private Context mContext = UserSignedInActivity.this;

    // VIEWS
    private ImageView backButton;
    private ImageView settingsButton;
    private LinearLayout profileLayout;
    private ImageView profileButton;
    private AdView mAdView;
    private Dialog mDialog;
    private RelativeLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signed_in);

        Log.d(TAG, "onCreate: Running...");

        // INITIALIZING VIEWS
        backButton = findViewById(R.id.user_signed_in_back_button);
        settingsButton = findViewById(R.id.user_signed_in_settings_button);
        profileLayout = findViewById(R.id.view_profile_layout);
        profileButton = findViewById(R.id.view_profile_button);
        mAdView = findViewById(R.id.user_signed_in_ad);
        adLayout = findViewById(R.id.signed_in_ad_view);
        mDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

//        loginText = findViewById(R.id.user_signed_in_username);
//        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue.ttf");
//        loginText.setTypeface(myFont);

        makeFullScreen();
        new DisplayAds(mAdView, adLayout);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButtonPressed(backButton);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings(settingsButton);
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profileLayout);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profileButton);
            }
        });

    }

    // TRANSPARENT STATUS BAR
    public void makeFullScreen() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // UI - BACK BUTTON
    public void backButtonPressed(View view) {
        finish();
    }

    // UI - SETTINGS BUTTON
    public void goToSettings(View view) {
        startActivity(new Intent(mContext, SettingsActivity.class));
    }

    // UI - SETUP POPUP
    public void showPopup(View view) {
        mDialog.setContentView(R.layout.layout_popup_register_or_login);

        final ImageView logo = mDialog.findViewById(R.id.popup_logo);
        ImageView closeButton = mDialog.findViewById(R.id.close_popup_button);
        TextView guest = mDialog.findViewById(R.id.guest_button);
        Button loginButton = mDialog.findViewById(R.id.popup_login_button);
        Button registerButton = mDialog.findViewById(R.id.popup_register_button);

        // DISMISS DIALOG
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharedIntent = new Intent(getApplicationContext(), UserLoginActivity.class);

                ActivityOptionsCompat options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                            logo, "logoTransition");
                    startActivity(sharedIntent);
                } else {
                    startActivity(sharedIntent);
                }
                mDialog.dismiss();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RegisterActivity1.class));
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }
}
