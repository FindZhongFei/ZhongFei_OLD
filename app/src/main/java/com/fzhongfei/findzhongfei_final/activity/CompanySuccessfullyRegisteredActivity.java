package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
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
import android.widget.ImageView;

import com.fzhongfei.findzhongfei_final.R;

public class CompanySuccessfullyRegisteredActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "SuccessfullyRegisteredA";
    private Context mContext = CompanySuccessfullyRegisteredActivity.this;

    // VIEWS
    ImageView mImageView;
    Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_successfully_registered);

        Log.d(TAG, "onCreate: Running....");

        // UI - FULLSCREEN NO TOOLBAR
        setUpActivityToolbar();

        // ANIMATING THE TICK
        mImageView = findViewById(R.id.scsImgView);
        ((Animatable) mImageView.getDrawable()).start();

        btnProfile = findViewById(R.id.btnShowProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
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

        mToolbar = findViewById(R.id.successfullyRegisteredToolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setTitleMarginStart(-70);
        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.registered);
    }

    private void goToProfile() {
        startActivity(new Intent(mContext, CompanyProfileActivity.class));
        finish();
    }


}
