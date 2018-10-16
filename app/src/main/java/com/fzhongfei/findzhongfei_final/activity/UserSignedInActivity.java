package com.fzhongfei.findzhongfei_final.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.utils.DisplayAds;
import com.google.android.gms.ads.AdView;

public class UserSignedInActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserSignedInActivity";
    private Context mContext = UserSignedInActivity.this;
    public static Activity finisher;

    // VIEWS
    private LinearLayout profileLayout;
    private ImageView profileButton;
    private AdView mAdView;
    private Dialog mDialog;
    private RelativeLayout adLayout;

    private LinearLayout loginTextDisplay;
    private LinearLayout hideIfNotLoggedIn;
    private TextView userNameText;
    private TextView userPhoneText;
    private TextView userEmailText;

    public static boolean userSignedIn;

    // Saving data locally
//    private SaveSharedPreferences mSaveSharedPreferenceUser = new SaveSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signed_in);

        Log.d(TAG, "onCreate: Running...");

        finisher = this;

        // INITIALIZING VIEWS
        profileLayout = findViewById(R.id.view_profile_layout);
        profileButton = findViewById(R.id.view_profile_button);
        mAdView = findViewById(R.id.user_signed_in_ad);
        adLayout = findViewById(R.id.signed_in_ad_view);
        mDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        loginTextDisplay = findViewById(R.id.login_text_if_not_logged_in);
        hideIfNotLoggedIn = findViewById(R.id.user_signed_in_hidden);
        userNameText = findViewById(R.id.user_signed_in_username);
        userPhoneText = findViewById(R.id.user_signed_in_phone_number);
        userEmailText = findViewById(R.id.user_signed_in_email);

//        loginText = findViewById(R.id.user_signed_in_username);
//        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue.ttf");
//        loginText.setTypeface(myFont);

        makeFullScreen();
        setUpActivityToolbar();
        new DisplayAds(mAdView, adLayout);

        Intent i = getIntent();
        userSignedIn = i.getBooleanExtra("isSignedIn", userSignedIn);

        Toast.makeText(mContext, String.valueOf(userSignedIn), Toast.LENGTH_SHORT).show();

        if(userSignedIn) {
            displayUserDetails();
            profileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToProfile();
                }
            });
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToProfile();
                }
            });
        } else {
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
    }

    // TRANSPARENT STATUS BAR
    public void makeFullScreen() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;

        mToolbar = findViewById(R.id.user_signed_in_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    // UI - BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // UI - SETTINGS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATING THE MENU
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }

    // UI - TOOLBAR ITEMS CLICKED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.profile_settings:
                startActivity(new Intent(mContext, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // UI - Displaying email and user name
    private void displayUserDetails() {
//        String userEmail = SaveSharedPreferences.getSharedPreferenceValue(mContext, SaveSharedPreferences.PREF_USER_EMAIL);
//        String userName = SaveSharedPreferences.getSharedPreferenceValue(mContext, SaveSharedPreferences.PREF_USER_FIRST_NAME + " " +
//                                                                                         SaveSharedPreferences.PREF_USER_LAST_NAME);
//        String userPhone = SaveSharedPreferences.getSharedPreferenceValue(mContext, SaveSharedPreferences.PREF_USER_PHONE);

        String userFirstName = UserRegisterActivity.sUserProfile.getUserFirstName();
        String userLastName = UserRegisterActivity.sUserProfile.getUserLastName();
        String userName = userFirstName + " " + userLastName;
        String userEmail = UserRegisterActivity.sUserProfile.getUserEmail();
        String userPhone = UserRegisterActivity.sUserProfile.getUserPhone();

        hideIfNotLoggedIn.setVisibility(View.VISIBLE);

        userNameText.setText(userName);
        userEmailText.setText(userEmail);
        userPhoneText.setText(userPhone);
    }

    // UI - USER DIDN'T LOGIN
    private void goToProfile() {
        startActivity(new Intent(mContext, UserProfileActivity.class));
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
