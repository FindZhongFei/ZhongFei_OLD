package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.fragments.FavoritesFragment;
import com.fzhongfei.findzhongfei_final.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;

    // VIEWS

    private boolean backButtonPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // BOTTOM NAVIGATION
        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // LOAD FRAGMENTS INTO VIEW
        loadFragment(new MainFragment());

        // CHECK IF USER IS LOGGED IN
        checkUserPreferences();
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
                    fragment = new MainFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.ic_fav:
                    fragment = new FavoritesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.ic_profile:
                    Intent i = new Intent(mContext, UserSignedInActivity.class);
                    i.putExtra("isSignedIn", UserSignedInActivity.userSignedIn);
                    mContext.startActivity(i);   // ACTIVITY_NUMBER = 2
                    return true;
            }
            return false;
        }
    };

    // UI - LOADING FRAGMENTS INSIDE VIEW
    private void loadFragment(Fragment fragment) {
        // LOAD FRAGMENTS
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void checkUserPreferences() {

    }
}
