package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class UserRegisterActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterActivity1";
    public Context mContext = UserRegisterActivity.this;

    // VIEWS
    private EditText firstNameEditText, lastNameEditText, emailAddressEditText, phoneNumberEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ProgressBar loading;

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

        loginButton.setOnClickListener(new View.OnClickListener() {
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
        registerRequest.startConnection(mContext, callBack, Params);

        if (!callBack.isSuccess()) {
            String errorMessage = callBack.getErrorMessage();
            if(errorMessage != null) {
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, (callBack.getSuccessMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    // SETTER FOR COMPANY PROFILE
    private void setCompanyFields() {
//        SaveSharedPreferences.setUserEmail(mContext, emailAddressEditText.getText().toString());
//        SaveSharedPreferences.setCompanyPhone(mContext, edtCompPhone.getText().toString());
//        SaveSharedPreferences.setCompanyEmail(mContext, edtCompEmail.getText().toString());
//        SaveSharedPreferences.setCompanyCeo(mContext, edtCompCEO.getText().toString());
//        SaveSharedPreferences.setCompanyRepresentative(mContext, edtRepName.getText().toString());
//        SaveSharedPreferences.setCompanyRepresentativeEmail(mContext, edtRepEmail.getText().toString());
    }
}
