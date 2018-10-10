package com.fzhongfei.findzhongfei_final.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.HistoryRVAdapter;
import com.fzhongfei.findzhongfei_final.model.HistoryItem;
import com.fzhongfei.findzhongfei_final.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserProfileActivity";
    private Context mContext = UserProfileActivity.this;
    public static Activity finisher;
    // VIEWS
    private ImageView profilePicture;
    private Dialog imageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.d(TAG, "onCreate: Running...");

        finisher = this;

        setUpActivityToolbar();
        setupRecyclerView();

        profilePicture = findViewById(R.id.user_big_profile_picture);
        imageDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profilePicture);
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
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);

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

//    // UI - EDIT PROFILE
//    public void editUserProfile(View view) {
//        startActivity(new Intent(mContext, UserProfileEditActivity.class));
//    }

    // UI - SETUP POPUP
    @SuppressLint("ClickableViewAccessibility")
    public void showPopup(View view) {
        imageDialog.setContentView(R.layout.layout_popup_full_image_view);
        ImageView closeButton = imageDialog.findViewById(R.id.close_popup_button);
        ImageView fullScreenImage = imageDialog.findViewById(R.id.fullview);

//        imageDialog.getWindow().setBackgroundDrawableResource(R.color.transparentBackground);

        // DISMISS DIALOG
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDialog.dismiss();
            }
        });

        fullScreenImage.setOnTouchListener(new OnSwipeTouchListener(mContext) {
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
        TextView historyTextView;
        RecyclerView mRecyclerView;
        HistoryRVAdapter mHistoryRVAdapter;
        List<HistoryItem> mHistoryItemList;

        historyTextView = findViewById(R.id.text_view_history);
//        int historyNumber = (Integer)mHistoryItemList.size();
//        Log.d(TAG, "setupRecyclerView: " + historyNumber);
        historyTextView.setText(getString(R.string.history, "12"));

        mHistoryItemList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view_history);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(
                this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

        mHistoryRVAdapter = new HistoryRVAdapter(mContext, mHistoryItemList);
        mRecyclerView.setAdapter(mHistoryRVAdapter);
    }
}
