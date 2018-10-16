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
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.util.HashMap;

public class UserRegisterActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterActivity1";
    public Context mContext = UserRegisterActivity.this;

    // VIEWS
    private EditText firstNameEditText, lastNameEditText, emailAddressEditText, phoneNumberEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ProgressBar loading;

    String mPassword, mConfirmPassword;

    // USER OBJECT
    public static UserProfile sUserProfile = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        TextView loginButton = findViewById(R.id.user_login_text_button);
        firstNameEditText = findViewById(R.id.user_register_first_name);
        lastNameEditText = findViewById(R.id.user_register_last_name);
        emailAddressEditText = findViewById(R.id.user_register_edit_email);
        phoneNumberEditText = findViewById(R.id.user_register_phone);
        passwordEditText = findViewById(R.id.user_register_edit_password);
        confirmPasswordEditText = findViewById(R.id.user_register_edit_confirm_password);
        registerButton = findViewById(R.id.user_register_button);
        loading = findViewById(R.id.loading_to_register_user);
        ImageView imageView = findViewById(R.id.register_logo);
        LinearLayout linearLayout = findViewById(R.id.register_linear_layout);

        // VALIDATE EDIT TEXTS
        firstNameEditText.addTextChangedListener(editTextTextWatcher);
        lastNameEditText.addTextChangedListener(editTextTextWatcher);
        emailAddressEditText.addTextChangedListener(editTextTextWatcher);
        phoneNumberEditText.addTextChangedListener(editTextTextWatcher);
        passwordEditText.addTextChangedListener(editTextTextWatcher);
        confirmPasswordEditText.addTextChangedListener(editTextTextWatcher);

        confirmPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN && InternetAvailability.internetIsAvailable(mContext)) {
                    if(registerButton.isEnabled()) {
                        if(mPassword.equals(mConfirmPassword)) {
                            setCompanyFields();
                            processRegistration();
                        } else {
                            confirmPasswordEditText.setError("Passwords donot match!");
                        }
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.error_comp_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetAvailability.internetIsAvailable(mContext)) {
                    processRegistration();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, UserLoginActivity.class));
                finish();
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
        SpannableStringBuilder spannable = new SpannableStringBuilder(getResources().getString(R.string.userLoginTxt));
        spannable.setSpan(
                new UnderlineSpan(),
                25, // start
                36, // end
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        loginButton.setText(spannable);
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
        mToolbar.setTitle(R.string.register);
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
            String mFirstName = firstNameEditText.getText().toString().trim();
            String mLastName = lastNameEditText.getText().toString().trim();
            String mEmailAddress = emailAddressEditText.getText().toString().trim();
            String mPhoneNumber = lastNameEditText.getText().toString().trim();
            mPassword = passwordEditText.getText().toString().trim();
            mConfirmPassword = confirmPasswordEditText.getText().toString().trim();

            registerButton.setEnabled(
                    !mFirstName.isEmpty() && !mLastName.isEmpty() &&
                            !mEmailAddress.isEmpty() && !mPhoneNumber.isEmpty() &&
                            !mPassword.isEmpty() && !mConfirmPassword.isEmpty());

            if(registerButton.isEnabled())
                registerButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_login_button));
            else {
                registerButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // REGISTER USER
    private void processRegistration() {
        final String firstNameValue, lastNameValue, emailAddressValue, phoneNumberValue, passwordValue, confirmPasswordValue;
        customStringRequest registerRequest = new customStringRequest("user/index.php");

        loading.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);

        firstNameValue = firstNameEditText.getText().toString();
        lastNameValue = lastNameEditText.getText().toString();
        emailAddressValue = emailAddressEditText.getText().toString();
        phoneNumberValue = phoneNumberEditText.getText().toString();
        passwordValue = passwordEditText.getText().toString();
        confirmPasswordValue = confirmPasswordEditText.getText().toString();

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "user_register");
        Params.put("user_firstName", firstNameValue);
        Params.put("user_lastName", lastNameValue);
        Params.put("user_email", emailAddressValue);
        Params.put("user_phone", phoneNumberValue);
        Params.put("user_password", passwordValue);
        Params.put("user_confirmPassword", confirmPasswordValue);

        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_registration");
        registerRequest.startConnection(mContext, callBack, Params);
    }

    // SETTER FOR COMPANY PROFILE
    private void setCompanyFields() {
        sUserProfile.setUserFirstName(firstNameEditText.getText().toString());
        sUserProfile.setUserLastName(lastNameEditText.getText().toString());
        sUserProfile.setUserEmail(emailAddressEditText.getText().toString());
        sUserProfile.setUserPhone(phoneNumberEditText.getText().toString());
    }
}
