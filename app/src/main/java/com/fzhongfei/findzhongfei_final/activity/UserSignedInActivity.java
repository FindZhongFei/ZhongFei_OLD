package com.fzhongfei.findzhongfei_final.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.utils.DisplayAds;
import com.google.android.gms.ads.AdView;

public class UserSignedInActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserSignedInActivity";
    private Context mContext = UserSignedInActivity.this;

    // VIEWS
    private LinearLayout profileLayout;
    private ImageView profileButton;
    private Dialog mDialog;
    private LinearLayout hideIfNotLoggedIn;
    private TextView userNameText;
    private TextView userPhoneText;
    private TextView userEmailText;

    private UserProfile sUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signed_in);

        Log.d(TAG, "onCreate: Running...");
        SharedPreferences companySharedPreferences = getSharedPreferences("companyPreference", 0);
        SharedPreferences userSharedPreferences = getSharedPreferences("userPreference", 0);

        // INITIALIZING VIEWS
        AdView mAdView = findViewById(R.id.user_signed_in_ad);
        profileLayout = findViewById(R.id.view_profile_layout);
        profileButton = findViewById(R.id.view_profile_button);
        RelativeLayout adLayout = findViewById(R.id.signed_in_ad_view);
        mDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        hideIfNotLoggedIn = findViewById(R.id.user_signed_in_hidden);
        userNameText = findViewById(R.id.user_signed_in_username);
        userPhoneText = findViewById(R.id.user_signed_in_phone_number);
        userEmailText = findViewById(R.id.user_signed_in_email);

//        LinearLayout loginTextDisplay = findViewById(R.id.login_text_if_not_logged_in);
//        loginText = findViewById(R.id.user_signed_in_username);
//        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue.ttf");
//        loginText.setTypeface(myFont);

        makeFullScreen();
        setUpActivityToolbar();
        new DisplayAds(mAdView, adLayout);

        if(!companySharedPreferences.contains("companyIsLoggedIn") && !userSharedPreferences.contains("userIsLoggedIn"))
        {
            profileLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { showPopup(profileLayout);
                    }
                });
            profileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { showPopup(profileButton);
                    }
                });
        }
        else if(companySharedPreferences.contains("companyIsLoggedIn"))
        {
            startActivity(new Intent(mContext, CompanyProfileActivity.class));
        }
        else if(userSharedPreferences.contains("userIsLoggedIn"))
        {
            sUserProfile = new UserProfile(mContext);
            sUserProfile.setPropertiesFromSharePreference(mContext);

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
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.user_signed_in_collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.user_signed_in_app_bar);

        mToolbar = findViewById(R.id.user_signed_in_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setTitleMarginStart(-100);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0 && sUserProfile.getUserLastName() != null)
                {
                    // Collapsed
                    collapsingToolbarLayout.setTitle(sUserProfile.getUserLastName());
                }
                else
                {
                    // Expanded
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });
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
                startActivity(new Intent(mContext, UserSettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // UI - Displaying email and user name
    private void displayUserDetails() {
        String firstNameValue = sUserProfile.getUserFirstName();
        String lastNameValue = sUserProfile.getUserLastName();
        String userEmailValue = sUserProfile.getUserEmail();
        String userPhoneValue = sUserProfile.getUserPhone();

        String userName = firstNameValue + " " + lastNameValue;

        hideIfNotLoggedIn.setVisibility(View.VISIBLE);

        userNameText.setText(userName);
        userEmailText.setText(userEmailValue);
        userPhoneText.setText(userPhoneValue);
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
                startActivity(new Intent(mContext, CompanyLoginActivity.class));
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }
}
