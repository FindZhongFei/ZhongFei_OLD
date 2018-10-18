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
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.util.HashMap;

public class CompanyLoginActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "CompanyLoginActivity";
    private Context mContext = CompanyLoginActivity.this;

    // VIEWS
    private EditText representativeEmail, companyPassword;
    private Button loginCompanyButton;
    private ProgressBar loading;
    private String mRepresentativeEmailValue, mPasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);

        Log.d(TAG, "onCreate: Running....");

        if(CompanyRegistrationActivity1.sCompanyProfile == null)
        {
            // TOOLBAR
            setUpActivityToolbar();

            // INITIALIZING VIEWS
            TextView registerButton = findViewById(R.id.comp_register_text_button);
            representativeEmail = findViewById(R.id.comp_login_edit_email_or_phone);
            companyPassword = findViewById(R.id.comp_login_edit_password);
            loginCompanyButton = findViewById(R.id.comp_login_button);
            loading = findViewById(R.id.loading_to_login_comp);
            LinearLayout linearLayout = findViewById(R.id.comp_login_linear_layout);

            // VALIDATE EDIT TEXTS
            representativeEmail.addTextChangedListener(editTextTextWatcher);
            companyPassword.addTextChangedListener(editTextTextWatcher);

            companyPassword.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN && InternetAvailability.internetIsAvailable(mContext)) {
                        if(loginCompanyButton.isEnabled()) {
                            processLogin();
                        } else {
                            Toast.makeText(mContext, getResources().getString(R.string.error_comp_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                        }
                    }

                    return false;
                }
            });

            loginCompanyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(InternetAvailability.internetIsAvailable(mContext)) {
                        processLogin();
                    }
                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard();
                }
            });
        }
        else
        {
            startActivity(new Intent(mContext, CompanyProfileActivity.class));
            finish();
        }
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

        mToolbar = findViewById(R.id.activity_login_comp_toolbar);
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
            mRepresentativeEmailValue = representativeEmail.getText().toString().trim();
            mPasswordValue = companyPassword.getText().toString().trim();

            loginCompanyButton.setEnabled(!mRepresentativeEmailValue.isEmpty() && !mPasswordValue.isEmpty());

            if(loginCompanyButton.isEnabled())
                loginCompanyButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_login_button));
            else
                loginCompanyButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // STOP LOADING
    public void stopCompanyLoginConnection() {
        loading.setVisibility(View.GONE);
        loginCompanyButton.setVisibility(View.VISIBLE);
    }

    // LOGIN COMPANY
    private void processLogin() {
        customStringRequest registerRequest = new customStringRequest("login/index.php");

        loading.setVisibility(View.VISIBLE);
        loginCompanyButton.setVisibility(View.GONE);

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "comp_login");
        Params.put("comp_representativeEmail", mRepresentativeEmailValue);
        Params.put("comp_password", mPasswordValue);

        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("comp_login");
        registerRequest.startConnection(mContext, callBack, Params);
    }
}
