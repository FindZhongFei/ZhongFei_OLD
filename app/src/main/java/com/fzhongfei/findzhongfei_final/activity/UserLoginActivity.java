package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.SharedPreferencesUser;

public class UserLoginActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserLoginActivity";
    private Context mContext = UserLoginActivity.this;

    // VIEWS
    private EditText username, password;
    private TextView registerButton;
    private Button loginButton;
    private ProgressBar loading;

    // Saving data locally
    private SharedPreferencesUser mSaveSharedPreferenceUser = new SharedPreferencesUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        username = findViewById(R.id.user_login_edit_username);
        password = findViewById(R.id.user_login_edit_password);
        registerButton = findViewById(R.id.user_register_text_button);
        loginButton = findViewById(R.id.user_login_button);
        loading = findViewById(R.id.loading_to_login_user);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if(!mUsername.isEmpty() && !mPassword.isEmpty()) {
                    attemptLogin(mUsername, mPassword);
                } else {
                    username.setError(getResources().getString(R.string.username_empty_error));
                    password.setError(getResources().getString(R.string.password_empty_error));
                }
            }
        });
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

        mToolbar = findViewById(R.id.activity_login_user_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.login);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // UI - LOGIN USER
    private void attemptLogin(String username, String password) {
        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        UserSignedInActivity.userSignedIn = true;

        SharedPreferencesUser.setUserEmail(mContext, "kingneud55@gmail.com");
        SharedPreferencesUser.setPhoneNumber(mContext, "+86 132 3657 6511");
        SharedPreferencesUser.setUsername(mContext, username.toString());
//        SharedPreferencesUser.setSharedPreferenceValue(mContext, SharedPreferencesUser.PREF_USER_USERNAME, username.toString());

        Intent i = new Intent(mContext, UserSignedInActivity.class);
        i.putExtra("isSignedIn", true);
        UserSignedInActivity.finisher.finish();
        startActivity(i);
        finish();
    }
}
