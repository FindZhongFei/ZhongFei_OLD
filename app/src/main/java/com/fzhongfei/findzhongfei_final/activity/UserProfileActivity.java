package com.fzhongfei.findzhongfei_final.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.HistoryRVAdapter;
import com.fzhongfei.findzhongfei_final.model.HistoryItem;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserProfileActivity";
    private Context mContext = UserProfileActivity.this;

    // VIEWS
    public static ImageView profilePicture, fullScreenProfilePicture;
    private ImageView closeButton;
    private TextView userNameText;
    private Dialog imageDialog;
    private UserProfile mUserProfile;

    private String userProfilePictureValue;

    // RECYCLER VIEW DATA
    TextView historyTextView;
    RecyclerView mRecyclerView;
    HistoryRVAdapter mHistoryRVAdapter;
    List<HistoryItem> mHistoryItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.d(TAG, "onCreate: Running...");

        // UI - TOOLBAR
        setUpActivityToolbar();

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        imageDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        imageDialog.setContentView(R.layout.layout_popup_full_image_view);
        closeButton = imageDialog.findViewById(R.id.close_popup_button);
        fullScreenProfilePicture = imageDialog.findViewById(R.id.profile_picture_full_screen);

        // UI - HISTORY
        historyTextView = findViewById(R.id.text_view_history);
        historyTextView.setText(getString(R.string.history, "12"));

        mHistoryItemList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view_history);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHistoryRVAdapter = new HistoryRVAdapter(mContext, mHistoryItemList);
        mRecyclerView.setAdapter(mHistoryRVAdapter);

        setupRecyclerView();

        if(mUserProfile.getUserIsLoggedIn())
        {
            profilePicture = findViewById(R.id.user_big_profile_picture);
            userNameText = findViewById(R.id.user_name);

            showUserProfile();

            profilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(profilePicture);
                }
            });
        } else {
            startActivity(new Intent(mContext, UserLoginActivity.class));
            finish();
        }
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

        mToolbar = findViewById(R.id.activity_user_profile_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setBackground(mGradientDrawable);
    }

    // TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // UI - MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATING THE MENU
        getMenuInflater().inflate(R.menu.menu_edit_user_profile, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // MENU ITEM CLICK HANDLING
        if(id == R.id.profile_edit) {
            startActivity(new Intent(mContext, UserProfileEditActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // UI - SETUP POPUP
    @SuppressLint("ClickableViewAccessibility")
    public void showPopup(View view) {
        // DISMISS DIALOG
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDialog.dismiss();
            }
        });

        fullScreenProfilePicture.setOnTouchListener(new OnSwipeTouchListener(mContext) {
            public void onSwipeTop() {
                imageDialog.dismiss();
            }
            public void onSwipeRight() {
                imageDialog.dismiss();
            }
            public void onSwipeLeft() {
                imageDialog.dismiss();
            }
            public void onSwipeBottom() {
                imageDialog.dismiss();
            }
        });

        imageDialog.show();
    }

    // UI - SETTING UP THE CARDS
    private void setupRecyclerView() {
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_11, "Nanjing University Of Aeronautics and Astronautics", "University"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_12, "BMW", "Car Maker"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_9, "Zemay", "Agency"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_15, "Dangote", "Cement Factory"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_1, "NUIST", "University"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_3, "Mercedes", "Car Maker"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_2, "LamboRari", "Car Dealership"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_5, "Mesobo Cement", "Cement Factory"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_4, "Nanjing University Of Aeronautics and Astronautics", "University"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_12, "BMW", "Car Maker"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_11, "Zemay", "Agency"));
        mHistoryItemList.add(new HistoryItem(R.drawable.img_wall_15, "Dangote", "Cement Factory"));
    }

    // ALL COMPANY PROFILE FIELDS
    private void showUserProfile() {
        if(mUserProfile.getUserIsLoggedIn())
        {
            String firstNameValue = mUserProfile.getUserFirstName();
            String lastNameValue = mUserProfile.getUserLastName();
//            String userEmailValue = mUserProfile.getUserEmail();
//            String userPhoneValue = mUserProfile.getUserPhone();

            userProfilePictureValue = mUserProfile.getUserProfilePicture();

            if(userProfilePictureValue != null) {
                byte[] decodedLogo = Base64.decode(userProfilePictureValue, Base64.DEFAULT);
                profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
                fullScreenProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
            }
            String username = firstNameValue + " " + lastNameValue;

            userNameText.setText(username);
        }
        else
        {
            Toast.makeText(mContext, "Please login", Toast.LENGTH_LONG).show();
            startActivity(new Intent(mContext, UserLoginActivity.class));
        }
    }
}
