package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.CompanyAdapter;
import com.fzhongfei.findzhongfei_final.model.Company;
import com.fzhongfei.findzhongfei_final.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;


public class FavoritesActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "FavoritesActivity";
    private Context mContext = FavoritesActivity.this;
    private static final int ACTIVITY_NUMBER = 1;

    // VIEWS
    RecyclerView mRecyclerView;

    CompanyAdapter mAdapter;
    List<Company> mCompanyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // BOTTOM NAVIGATION
        setUpBottomNavigation();

        // LIST COMPANIES
        setupRecyclerView();
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

        mToolbar = findViewById(R.id.activity_favorites_toolbar);
        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.favorites);
    }

    // UI - SETTING UP FAVORITES
    private void setupRecyclerView() {
        mCompanyList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.favRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_10,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_14,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_9,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_15,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_2,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_3,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_12,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mAdapter = new CompanyAdapter(mContext, mCompanyList);
        mRecyclerView.setAdapter(mAdapter);
    }

    // UI - SETTING UP THE BOTTOM NAVIGATION
    private void setUpBottomNavigation() {
        Log.d(TAG, "setUpBottomNavigation: setting up the BottomNavigationView");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        BottomNavigationViewHelper.setUpBottomNavView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);

        // HIGHLIGHTING THE RIGHT MENU ITEMS
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }
}
