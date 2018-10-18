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
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.util.HashMap;

import static com.fzhongfei.findzhongfei_final.activity.UserRegistrationActivity.sUserProfile;

public class UserLoginActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserLoginActivity";
    private Context mContext = UserLoginActivity.this;

    // VIEWS
    private EditText userEmailOrPhone, userPassword;
    private Button loginButton;
    private ProgressBar loading;
    private String mUsernameValue, mPasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        TextView registerButton = findViewById(R.id.user_register_text_button);
        userEmailOrPhone = findViewById(R.id.user_login_edit_email_or_phone);
        userPassword = findViewById(R.id.user_login_edit_password);
        loginButton = findViewById(R.id.user_login_button);
        loading = findViewById(R.id.loading_to_login_user);
        ImageView imageView = findViewById(R.id.login_logo);
        LinearLayout linearLayout = findViewById(R.id.user_login_linear_layout);

        // VALIDATE EDIT TEXTS
        userEmailOrPhone.addTextChangedListener(editTextTextWatcher);
        userPassword.addTextChangedListener(editTextTextWatcher);

        userPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN && InternetAvailability.internetIsAvailable(mContext)) {
                    if(loginButton.isEnabled()) {
                        processLogin();
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
                    processLogin();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetAvailability.internetIsAvailable(mContext)) {
                    startActivity(new Intent(mContext, UserRegistrationActivity.class));
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
            mUsernameValue = userEmailOrPhone.getText().toString().trim();
            mPasswordValue = userPassword.getText().toString().trim();

            loginButton.setEnabled(!mUsernameValue.isEmpty() && !mPasswordValue.isEmpty());

            if(loginButton.isEnabled())
                loginButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_login_button));
            else
                loginButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // STOP LOADING
    public void stopUserLoginConnection() {
        loading.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

    // LOGIN USER
    private void processLogin() {
        final String usernameTypeValue;
        customStringRequest registerRequest = new customStringRequest("user/login.php");

        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        if(mUsernameValue.contains("@")) {
            usernameTypeValue = "isEmail";
        } else {
            usernameTypeValue = "isPhone";
        }

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "user_login");
        Params.put("user_username", mUsernameValue);
        Params.put("user_usernameType", usernameTypeValue);
        Params.put("user_password", mPasswordValue);

//        Params.put("phone_serial_number", Build.SERIAL);
//        Params.put("phone_model_number", Build.MODEL);
//        Params.put("phone_id_number", Build.ID);
//        Params.put("phone_manufacturer", Build.MANUFACTURER);
//        Params.put("phone_brand", Build.BRAND);
//        Params.put("phone_type", Build.TYPE);
//        Params.put("phone_user", Build.USER);
//        Params.put("phone_base", String.valueOf(Build.VERSION_CODES.BASE));
//        Params.put("phone_sdk_version", String.valueOf(Build.VERSION.SDK_INT));
//        Params.put("phone_host", Build.HOST);
//        Params.put("phone_fingerprint", Build.FINGERPRINT);
//        Params.put("phone_release", Build.VERSION.RELEASE);

        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_login");
        registerRequest.startConnection(mContext, callBack, Params);
    }

    // UI - LOGIN USER
    private void attemptLogin() {
        UserSignedInActivity.userSignedIn = true;
        String signedInWith = userEmailOrPhone.getText().toString().trim();
        String userName = sUserProfile.getUserFirstName() + " " + sUserProfile.getUserLastName();

        if(signedInWith.contains("@")) {
            sUserProfile.setUserEmail(signedInWith);
        } else {
            sUserProfile.setUserPhone(signedInWith);
        }

        UserSignedInActivity.userSignedIn = true;

        Intent i = new Intent(mContext, UserSignedInActivity.class);
        i.putExtra("isSignedIn", true);
        ((UserSignedInActivity) mContext).finish();
        startActivity(i);
        finish();
    }
}
