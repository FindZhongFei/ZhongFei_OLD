package com.fzhongfei.findzhongfei_final.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.DisplayAds;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;

public class UserSignedInActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserSignedInActivity";
    private Context mContext = UserSignedInActivity.this;

    // VIEWS
    private LinearLayout profileLayout;
    private ImageView profileButton;
    public static ImageView userProfilePicture;
    private Dialog mDialog;
    private LinearLayout hideIfNotLoggedIn;
    private TextView userNameText, userPhoneText, userEmailText;

    private Boolean rememberedUser, rememberedCompany;

    private UserProfile mUserProfile;

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
        userProfilePicture = findViewById(R.id.user_signed_in_profile_picture);
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
            rememberedUser = false;
            rememberedCompany = false;
        }
        else if(companySharedPreferences.contains("companyIsLoggedIn"))
        {
            rememberedCompany = true;
            startActivity(new Intent(mContext, CompanyProfileActivity.class));
        }
        else if(userSharedPreferences.contains("userIsLoggedIn"))
        {
            mUserProfile = new UserProfile(mContext);
            mUserProfile.setPropertiesFromSharePreference(mContext);

            rememberedUser = true;
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
        final Toolbar mToolbar;
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
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0)
                {
                    // Collapsed
//                    mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_ATOP);
                    if(rememberedUser)
                    {
                        collapsingToolbarLayout.setTitle(mUserProfile.getUserLastName());
                    }
                    else
                    {
                        collapsingToolbarLayout.setTitle("Profile");
                    }
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
        customStringRequest imageRequest = new customStringRequest();

        String userTokenValue = mUserProfile.getUserToken();
        String userProfileUrl = mUserProfile.getUserProfileUrl();
        String userProfilePictureValue = mUserProfile.getUserProfilePicture();
        Boolean profilePictureIsDownloaded = mUserProfile.isUserProfileDownloaded();

        if(!profilePictureIsDownloaded || userProfilePictureValue == null)
        {
            HashMap<String, String> params = new HashMap<>();

            params.put("requestType", "requestUserPicture");
            params.put("profilePictureUrl", userProfileUrl);
            params.put("userToken", userTokenValue);

            imageRequest.setUrlPath("user/fetchImage.php");
            imageRequest.setParams(params);

            callBackImplement callBack = new callBackImplement(mContext);
            callBack.setParams(params);
            callBack.SetRequestType("requestUserProfilePicture");

            imageRequest.startConnection(mContext, callBack, params);
        }
        else
        {
            byte[] decodedLogo = Base64.decode(userProfilePictureValue, Base64.DEFAULT);
            userProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
        }

        String firstNameValue = mUserProfile.getUserFirstName();
        String lastNameValue = mUserProfile.getUserLastName();
        String userEmailValue = mUserProfile.getUserEmail();
        String userPhoneValue = mUserProfile.getUserPhone();

        String userName = firstNameValue + " " + lastNameValue;
        String phoneNumber = "+" + userPhoneValue;

        hideIfNotLoggedIn.setVisibility(View.VISIBLE);

        userNameText.setText(userName);
        userEmailText.setText(userEmailValue);
        userPhoneText.setText(phoneNumber);
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
                startActivity(new Intent(mContext, CompanyRegistrationActivity1.class));
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }
}
