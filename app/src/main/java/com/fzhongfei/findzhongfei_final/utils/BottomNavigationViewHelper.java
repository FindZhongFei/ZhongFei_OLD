package com.fzhongfei.findzhongfei_final.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.FavoritesActivity;
import com.fzhongfei.findzhongfei_final.activity.MainActivity;
import com.fzhongfei.findzhongfei_final.activity.UserSignedInActivity;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setUpBottomNavView(BottomNavigationView bottomNavView) {
        Log.d(TAG, "setUpBottomNavView: Setting up BottomNavigation view");
    }

    public static void enableNavigation(final Context context, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intentHome = new Intent(context, MainActivity.class);    // ACTIVITY_NUMBER = 0
                        context.startActivity(intentHome);
                        ((Activity)(context)).finish();
                        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_fav:
                        Intent intentFav = new Intent(context, FavoritesActivity.class);    // ACTIVITY_NUMBER = 1
                        context.startActivity(intentFav);
                        ((Activity)(context)).finish();
                        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_profile:
                        Intent intentProfile = new Intent(context, UserSignedInActivity.class);  // ACTIVITY_NUMBER = 2
                        context.startActivity(intentProfile);
                        break;
                }

                return false;
            }
        });
    }

    public static class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

        public BottomNavigationBehavior() {
            super();
        }

        public BottomNavigationBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
            boolean dependsOn = dependency instanceof FrameLayout;
            return dependsOn;
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child,
                                           View directTargetChild, View target, int nestedScrollAxes) {
            return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child,
                                      View target, int dx, int dy, int[] consumed) {
            if (dy < 0) {
                showBottomNavigationView(child);
            } else if (dy > 0) {
                hideBottomNavigationView(child);
            }
        }

        private void hideBottomNavigationView(BottomNavigationView view) {
            view.animate().translationY(view.getHeight());
        }

        private void showBottomNavigationView(BottomNavigationView view) {
            view.animate().translationY(0);
        }

    }
}
