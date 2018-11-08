package com.fzhongfei.findzhongfei_final.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.fzhongfei.findzhongfei_final.R;

public class CompanySuccessfullyRegisteredActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "SuccessfullyRegisteredA";
    private Context mContext = CompanySuccessfullyRegisteredActivity.this;

    // VIEWS
    ImageView mImageView, mImageView2;
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
        mImageView2 = findViewById(R.id.scsImgView2);
        ((Animatable) mImageView.getDrawable()).start();

        AnimationSet s = new AnimationSet(false);   // false means don't share interpolators
        AnimationSet s2 = new AnimationSet(false);
        final Animation pulse1 = AnimationUtils.loadAnimation(mContext, R.anim.pulse);
        final Animation pulse2 = AnimationUtils.loadAnimation(mContext, R.anim.pulse2);

        pulse1.setStartOffset(500);
        pulse2.setStartOffset(1000);
        pulse2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                pulse2.setStartOffset(0);
            }
        });

        s2.addAnimation(pulse1);
        mImageView.startAnimation(s2);

//        pulse2.setStartOffset(500);
        s.addAnimation(pulse2);
        mImageView2.startAnimation(pulse2);

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
