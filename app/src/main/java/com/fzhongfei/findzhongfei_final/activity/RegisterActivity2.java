package com.fzhongfei.findzhongfei_final.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterActivity2";
    private Context mContext = RegisterActivity2.this;

    // VIEWS
    public static ProgressDialog dialog;
    private Button nextRegistrationButton;
    private EditText edtAddress1, edtAddress2, edtCity, edtProvince;

    final VolleyError volleyError = new VolleyError();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register2);

        Log.d(TAG, "onCreate: Running...");

        // TOOLBAR
        setUpActivityToolbar();

        // TURN ON INTERNET
        InternetAvailability.internetIsAvailable(mContext);

        // INITIALIZING VIEWS
        edtAddress1 = findViewById(R.id.in_add1);
        edtAddress2 = findViewById(R.id.in_add2);
        edtCity = findViewById(R.id.in_city);
        edtProvince = findViewById(R.id.in_province);
        nextRegistrationButton = findViewById(R.id.btnNextRegister2);
        LinearLayout mLinearLayout = findViewById(R.id.register2_linear_layout);

        // VALIDATE EDIT TEXTS
        edtAddress1.addTextChangedListener(editTextTextWatcher);
        edtAddress2.addTextChangedListener(editTextTextWatcher);
        edtCity.addTextChangedListener(editTextTextWatcher);
        edtProvince.addTextChangedListener(editTextTextWatcher);

        // DIALOG FOR REGISTERING
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setProgress(0);

        edtProvince.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN && InternetAvailability.internetIsAvailable(mContext)) {
                    if(nextRegistrationButton.isEnabled()) {
                        setCompanyFields();
                        processRegistration();
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.error_comp_fill_in_all_fields), Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        nextRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetAvailability.internetIsAvailable(mContext)) {
                    setCompanyFields();
                    processRegistration();
                }
            }
        });

        // HIDE KEYBOARD WHEN CLICKED OUTSIDE EDIT TEXT
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(inputMethodManager != null && getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), 0);
                }
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

        mToolbar = findViewById(R.id.activity_register2_toolbar);
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

    // UI - VALIDATION
    private TextWatcher editTextTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String address1 = edtAddress1.getText().toString().trim();
            String address2 = edtAddress2.getText().toString().trim();
            String city = edtCity.getText().toString().trim();
            String province = edtProvince.getText().toString().trim();

            nextRegistrationButton.setEnabled(
                            !address1.isEmpty() && !address2.isEmpty() &&
                            !city.isEmpty() && !province.isEmpty());

            if(nextRegistrationButton.isEnabled())
                nextRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background));
            else
                nextRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public static void stopConnection2() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    // FORM FILLED
    @TargetApi(Build.VERSION_CODES.O)
    private void processRegistration() {
        final String comp_add1, comp_add2, comp_city, comp_province, comp_token;

        dialog.show();

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    Toast.makeText(mContext, "Process is taking longer than usual, " +
                            "please check your internet connection", Toast.LENGTH_SHORT).show();
                } else if(volleyError.getMessage() != null && dialog.isShowing()) {
                    Toast.makeText(mContext, "Volley Error" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 100 * 300);

        comp_add1 = edtAddress1.getText().toString();
        comp_add2 = edtAddress2.getText().toString();
        comp_city = edtCity.getText().toString();
        comp_province = edtProvince.getText().toString();
        comp_token = getIntent().getStringExtra("comp_token");

        HashMap<String, String> Params = new HashMap<>();
        Params.put("comp_add1", comp_add1);
        Params.put("comp_add2", comp_add2);
        Params.put("comp_city", comp_city);
        Params.put("comp_province", comp_province);
        Params.put("comp_token", comp_token);

        customStringRequest registerRequest = new customStringRequest("register/index.php");
        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("comp_registration");
        registerRequest.startConnection(mContext, callBack, Params);
    }

    // SETTER FOR COMPANY PROFILE
    private void setCompanyFields() {
        RegisterActivity1.sCompanyProfile.setCompanyAddress1(mContext, edtAddress1.getText().toString());
        RegisterActivity1.sCompanyProfile.setCompanyAddress2(mContext, edtAddress2.getText().toString());
        RegisterActivity1.sCompanyProfile.setCompanyCity(mContext, edtCity.getText().toString());
        RegisterActivity1.sCompanyProfile.setCompanyProvince(mContext, edtProvince.getText().toString());
    }
}
