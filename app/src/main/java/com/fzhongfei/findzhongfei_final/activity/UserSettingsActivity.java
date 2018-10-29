package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;

public class UserSettingsActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserSettingsActivity";
    private Context mContext = UserSettingsActivity.this;

    // VIEWS
    Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        Log.d(TAG, "onCreate: Running");

        // VIEWS
        signOutBtn = findViewById(R.id.company_signout_button);

        SharedPreferences sharedPreferences = getSharedPreferences("companyPreference", 0);
        if(sharedPreferences.contains("companyIsLoggedIn")) {
            signOutBtn.setVisibility(View.VISIBLE);
        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutCompany();
            }
        });

        // TOOLBAR
        setUpActivityToolbar();
    }

    // SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;
        Window window;
        GradientDrawable mGradientDrawable;

        mGradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] {0xFF5258A6,0xFF7375B7});

        // CHANGE THE STATUS BAR COLOR TO TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(mContext.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(mContext.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(mGradientDrawable);
        }

        mToolbar = findViewById(R.id.activity_settings_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setTitleMarginStart(-70);
        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.settingsTitle);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // UI - SIGN OUT
    public void signOutCompany() {
        Intent i = new Intent(mContext, MainActivity.class);
        new CompanyProfile(mContext).clearSharedPreference(mContext);
        finishAffinity();
        startActivity(i);
        finish();
    }
}
