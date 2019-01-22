package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.fragments.ChatFragment;
import com.fzhongfei.findzhongfei_final.fragments.DiscoverFragment;
import com.fzhongfei.findzhongfei_final.fragments.FavoritesFragment;
import com.fzhongfei.findzhongfei_final.fragments.MainFragment1;
import com.fzhongfei.findzhongfei_final.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;

    // VIEWS
    final FragmentManager mFragmentManager = getSupportFragmentManager();
    final Fragment mainFragment = new MainFragment1();
    final Fragment favoriteFragment = new FavoritesFragment();
    final Fragment chatFragment = new ChatFragment();
    final Fragment discoverFragment = new DiscoverFragment();
    Fragment currentFragment;
    private boolean backButtonPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Running...");

//        CompanyProfile companyProfile = new CompanyProfile(mContext);
//        companyProfile.clearSharedPreference(mContext);

        // TOOLBAR
        setUpActivityToolbar();

        // BOTTOM NAVIGATION
        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        if(savedInstanceState == null)
        {
            // LOAD MAIN FRAGMENTS INTO VIEW
            loadFragment(mainFragment, "MainFragment");
            currentFragment = mainFragment;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (backButtonPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.backButtonPressedOnce = true;
        Toast.makeText(this, R.string.pressTwiceToExit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backButtonPressedOnce=false;
            }
        }, 2000);
    }

    // UI - SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Window window;

        // CHANGE THE STATUS BAR COLOR TO TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    // UI - SETTING UP THE BOTTOM NAVIGATION VIEW
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.ic_home:
                    loadFragment(mainFragment, "MainFragment");
                    currentFragment = mainFragment;
                    return true;
                case R.id.ic_fav:
                    loadFragment(chatFragment, "ChatFragment");
                    currentFragment = chatFragment;
                    return true;
                case R.id.ic_dis:
                    loadFragment(discoverFragment, "DiscoverFragment");
                    currentFragment = discoverFragment;
                    return true;
                case R.id.ic_profile:
                    mContext.startActivity(new Intent(mContext, UserSignedInActivity.class));   // ACTIVITY_NUMBER = 2
                    return true;
            }
            return false;
        }
    };

    // UI - LOADING FRAGMENTS INSIDE VIEW
    private void loadFragment(Fragment fragment, String tag) {
        // LOAD FRAGMENTS
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        transaction.commit();
    }
}
