package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;

public class UserProfileEditActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserProfileEditActivity";
    private Context mContext = UserProfileEditActivity.this;

    // VIEWS
    private TextView signoutText;
    private TextView signoutButton;
    private TextView progressPercentage;
    private ProgressBar signoutProgressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        Log.d(TAG, "onCreate: Running...");

        signoutText = findViewById(R.id.user_profile_signout_text);
        signoutButton = findViewById(R.id.sign_out_button);
//        signoutProgressBar = findViewById(R.id.progress_bar);
        progressPercentage = findViewById(R.id.txtProgress);

        // TOOLBAR
        setUpActivityToolbar();

        // SIGN OUT
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                signoutUser();
                startActivity(new Intent(mContext, UserSignedInActivity.class));
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

        mToolbar = findViewById(R.id.activity_user_profile_edit_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.profileTitle);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // UI - SIGN OUT
//    public void signoutUser() {
//        signoutText.setVisibility(View.GONE);
//        signoutProgressBar.setVisibility(View.VISIBLE);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (pStatus <= 100) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            signoutProgressBar.setProgress(pStatus);
//                            progressPercentage.setText(pStatus + " %");
//                        }
//                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    pStatus++;
//                }
//            }
//        }).start();
//    }
}
