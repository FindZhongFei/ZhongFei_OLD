package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import static com.fzhongfei.findzhongfei_final.activity.UserRegisterActivity.sUserProfile;

public class UserLoginActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserLoginActivity";
    private Context mContext = UserLoginActivity.this;

    // VIEWS
    private EditText username, password;
    private Button loginButton;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        TextView registerButton = findViewById(R.id.user_register_text_button);
        username = findViewById(R.id.user_login_edit_email_or_phone);
        password = findViewById(R.id.user_login_edit_password);
        loginButton = findViewById(R.id.user_login_button);
        loading = findViewById(R.id.loading_to_login_user);
        ImageView imageView = findViewById(R.id.login_logo);
        LinearLayout linearLayout = findViewById(R.id.login_linear_layout);

        // VALIDATE EDIT TEXTS
        username.addTextChangedListener(editTextTextWatcher);
        password.addTextChangedListener(editTextTextWatcher);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN && InternetAvailability.internetIsAvailable(mContext)) {
                    if(loginButton.isEnabled()) {
                        attemptLogin();
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.error_comp_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetAvailability.internetIsAvailable(mContext)) {
                    attemptLogin();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetAvailability.internetIsAvailable(mContext)) {
                    startActivity(new Intent(mContext, UserRegisterActivity.class));
                    finish();
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });

        // CHANGING COLOR OF THE 'REGISTER HERE' TEXT VIEW
        SpannableStringBuilder spannable = new SpannableStringBuilder(getResources().getString(R.string.notAUserRegisterTxt));
        spannable.setSpan(
                new UnderlineSpan(),
                27, // start
                40, // end
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        registerButton.setText(spannable);
    }

    // UI - SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;
        Window window;
        GradientDrawable mGradientDrawable;

        mGradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{0xFF5258A6, 0xFF7375B7});

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

    // UI - HIDE KEYBOARD
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), 0);
        }
    }

    // UI - VALIDATION
    private TextWatcher editTextTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String mUsername = username.getText().toString().trim();
            String mPassword = password.getText().toString().trim();

            loginButton.setEnabled(!mUsername.isEmpty() && !mPassword.isEmpty());

            if(loginButton.isEnabled())
                loginButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_login_button));
            else
                loginButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // UI - LOGIN USER
    private void attemptLogin() {
        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        UserSignedInActivity.userSignedIn = true;
        String signedInWith = username.getText().toString().trim();
        String userName = sUserProfile.getUserFirstName() + " " + sUserProfile.getUserLastName();

        if(signedInWith.contains("@")) {
            sUserProfile.setUserEmail(signedInWith);
        } else {
            sUserProfile.setUserPhone(signedInWith);
        }
//        SaveSharedPreferences.setSharedPreferenceValue(mContext, SaveSharedPreferences.PREF_USER_USERNAME, username.toString());

        UserSignedInActivity.userSignedIn = true;

        Intent i = new Intent(mContext, UserSignedInActivity.class);
        i.putExtra("isSignedIn", true);
        UserSignedInActivity.finisher.finish();
        startActivity(i);
        finish();
    }
}
