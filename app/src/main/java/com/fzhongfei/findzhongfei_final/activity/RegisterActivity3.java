package com.fzhongfei.findzhongfei_final.activity;

import android.annotation.SuppressLint;
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
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.SaveSharedPreferences;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.util.HashMap;

public class RegisterActivity3 extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterActivity3";
    private Context mContext = RegisterActivity3.this;

    // VIEWS
    public static ProgressDialog dialog;
    private Button doneRegistrationButton;
    private ScrollView mScrollView;
    private EditText textScroll;
    private Spinner compTypeSpinner;
    private Spinner compSubTypeSpinner;
    private EditText edtCompType, edtCompSubType, edtWechatId;
    private LinearLayout hiddenCompTypeLayout, hiddenCompSubTypeLayout;
    private Boolean custom_comp_type = false;
    private Boolean custom_comp_sub_type = false;

    final VolleyError volleyError = new VolleyError();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        Log.d(TAG, "onCreate: Running...");

        // TURN ON INTERNET
        InternetAvailability.internetIsAvailable(mContext);

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        edtWechatId = findViewById(R.id.in_wechatid);
        edtCompType = findViewById(R.id.in_comptype);
        edtCompSubType = findViewById(R.id.in_subtype);
        mScrollView = findViewById(R.id.register_scrollView3);
        textScroll = findViewById(R.id.in_desc);
        doneRegistrationButton = findViewById(R.id.btnDoneRegister3);
        compTypeSpinner = findViewById(R.id.spinner_company_type);
        compSubTypeSpinner = findViewById(R.id.spinner_company_sub_type);
        hiddenCompTypeLayout = findViewById(R.id.hiddenCompanyType);
        hiddenCompSubTypeLayout = findViewById(R.id.hiddenCompanySubType);
        LinearLayout mLinearLayout = findViewById(R.id.register3_linear_layout);

        // VALIDATE EDIT TEXTS
        edtCompType.addTextChangedListener(editTextTextWatcher);
        edtCompSubType.addTextChangedListener(editTextTextWatcher);
        edtWechatId.addTextChangedListener(editTextTextWatcher);
        textScroll.addTextChangedListener(editTextTextWatcher);

        // DIALOG FOR REGISTERING
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setProgress(0);

        compTypeSpinner.setOnItemSelectedListener(new CustomOnItemClickedListener());
        compSubTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(adapterView.getItemAtPosition(pos).toString().equals("Other")) {
                    custom_comp_sub_type = true;
                    hiddenCompSubTypeLayout.setVisibility(View.VISIBLE);
                } else {
                    custom_comp_sub_type = false;
                    hiddenCompSubTypeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        doneRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetAvailability.internetIsAvailable(mContext)) {
                    setCompanyFields();
                    processRegistration();
                }
            }
        });

        textScroll.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textScroll.setRawInputType(InputType.TYPE_CLASS_TEXT);
        textScroll.setVerticalScrollBarEnabled(true);
        textScroll.isVerticalScrollBarEnabled();

        textScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("TAG", "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of
                // child view
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    mScrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    mScrollView.requestDisallowInterceptTouchEvent(true);
                }

                return false;
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

        mToolbar = findViewById(R.id.activity_register3_toolbar);
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
            String companyType = edtCompType.getText().toString().trim();
            String companySubType = edtCompSubType.getText().toString().trim();
            String wechatId = edtWechatId.getText().toString().trim();
            String description = textScroll.getText().toString().trim();

            if(custom_comp_type){
                doneRegistrationButton.setEnabled(
                                !companyType.isEmpty() && !companySubType.isEmpty() &&
                                !wechatId.isEmpty() && !description.isEmpty());
            } else if(custom_comp_sub_type) {
                doneRegistrationButton.setEnabled(!companySubType.isEmpty() &&
                                !wechatId.isEmpty() && !description.isEmpty());
            } else {
                doneRegistrationButton.setEnabled(!wechatId.isEmpty() && !description.isEmpty());
            }

            if(doneRegistrationButton.isEnabled())
                doneRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background));
            else {
                doneRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public static void stopConnection3() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    // FORM FILLED
    @TargetApi(Build.VERSION_CODES.O)
    private void processRegistration() {
        final String comp_type, comp_subType, wechatId, desc, comp_token, comp_custom_type, comp_custom_sub_type;

        dialog.show();

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    Toast.makeText(mContext, "Process is taking longer than usual please check your internet connection", Toast.LENGTH_SHORT)
                            .show();
                } else if(volleyError.getMessage() != null && dialog.isShowing()) {
                    Toast.makeText(mContext, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 100 * 300);

        comp_type = String.valueOf(compTypeSpinner.getSelectedItem());
        comp_subType = compSubTypeSpinner.getSelectedItem().toString();
        wechatId = edtWechatId.getText().toString();
        desc = textScroll.getText().toString();
        comp_custom_type = edtCompType.getText().toString();
        comp_custom_sub_type = edtCompSubType.getText().toString();
        comp_token = getIntent().getStringExtra("comp_token");

        HashMap<String, String> Params = new HashMap<>();

        if(comp_type.equals("Other") && comp_subType.equals("Other")) {
            custom_comp_type = true;
            custom_comp_sub_type = true;

            Params.put("comp_customType", comp_custom_type);
            Params.put("comp_customSubType", comp_custom_sub_type);
        } else if(comp_type.equals("Other")) {
            custom_comp_type = true;

            Params.put("comp_customType", comp_custom_type);
        } else if(comp_subType.equals("Other")) {
            custom_comp_sub_type = true;

            Params.put("comp_customSubType", comp_custom_sub_type);
        }

        Params.put("comp_type", comp_type);
        Params.put("comp_subType", comp_subType);
        Params.put("comp_wechatId", wechatId);
        Params.put("comp_desc", desc);
        Params.put("comp_token", comp_token);
        Params.put("comp_typeIsOther", custom_comp_type.toString());
        Params.put("comp_subTypeIsOther", custom_comp_sub_type.toString());

        customStringRequest registerRequest = new customStringRequest("register/index.php");
        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        registerRequest.startConnection(mContext, callBack, Params);

        if (!callBack.isSuccess()) {
            String errorMessage = callBack.getErrorMessage();
            if(errorMessage != null) {
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "NEUD===========================processRegistration: is success is: " + errorMessage);
//                Log.d(TAG, "NEUD===========================processRegistration: success message after failure: " + callBack.getSuccessMessage());
            }
//        } else {
//            Toast.makeText(mContext, (callBack.getSuccessMessage()), Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "NEUD===========================processRegistration: success message " + callBack.getSuccessMessage());
        }
    }

    // SETTER FOR COMPANY PROFILE
    private void setCompanyFields() {
        if(custom_comp_type && custom_comp_sub_type) {
            SaveSharedPreferences.setCompanyType(mContext, edtCompType.getText().toString());
            SaveSharedPreferences.setCompanySubType(mContext, edtCompSubType.getText().toString());
//        SaveSharedPreferences.setCompanyWechatId(mContext, edtWechatId.getText().toString());
//        SaveSharedPreferences.setCompanyDescription(mContext, textScroll.getText().toString());
        } else if(custom_comp_sub_type) {
            SaveSharedPreferences.setCompanyType(mContext, compTypeSpinner.getSelectedItem().toString());
            SaveSharedPreferences.setCompanySubType(mContext, edtCompSubType.getText().toString());
        } else {
            SaveSharedPreferences.setCompanyType(mContext, compTypeSpinner.getSelectedItem().toString());
            SaveSharedPreferences.setCompanySubType(mContext, compSubTypeSpinner.getSelectedItem().toString());
        }
    }

    // INNER CLASS
    public class CustomOnItemClickedListener implements OnItemSelectedListener {
        String subTypeList[];

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            switch(pos) {
                case 1:             // AGRICULTURE
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesAgriculture)[0],
                                                getResources().getStringArray(R.array.subTypesAgriculture)[1],
                                                getResources().getStringArray(R.array.subTypesAgriculture)[2],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 2:             // CHEMICAL
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesChemical)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 3:             // COMPUTER
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesComputer)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 5:             // DEFENSE
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesDefense)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 7:             // ENERGY
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesEnergy)[0],
                                                getResources().getStringArray(R.array.subTypesEnergy)[1],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 9:             // FINANCIAL
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesFinancial)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    break;
                case 10:            // FOOD
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesFood)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 14:            // MANUFACTURING
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesManufacturing)[0],
                                                getResources().getStringArray(R.array.subTypesManufacturing)[1],
                                                getResources().getStringArray(R.array.subTypesManufacturing)[2],
                                                getResources().getStringArray(R.array.subTypesManufacturing)[3],
                                                getResources().getStringArray(R.array.subTypesManufacturing)[4],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 15:            // MEDIA
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesMedia)[0],
                                                getResources().getStringArray(R.array.subTypesMedia)[1],
                                                getResources().getStringArray(R.array.subTypesMedia)[2],
                                                getResources().getStringArray(R.array.subTypesMedia)[3],
                                                getResources().getStringArray(R.array.subTypesMedia)[4],
                                                getResources().getStringArray(R.array.subTypesMedia)[5],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    break;
                case 17:            // TELECOMMUNICATIONS
                    subTypeList = new String[]{ getResources().getStringArray(R.array.subTypesTelecommunications)[0],
                                                getResources().getStringArray(R.array.subTypesOther)[0]};
                    hideFields();
                    break;
                case 21:            // OTHER
                    subTypeList = new String[]{getResources().getStringArray(R.array.subTypesOther)[0]};
                    hiddenCompTypeLayout.setVisibility(View.VISIBLE);
                    hiddenCompSubTypeLayout.setVisibility(View.VISIBLE);
                    custom_comp_type = true;
                    custom_comp_sub_type = true;
                    break;

                    default:
                        subTypeList = new String[]{getResources().getStringArray(R.array.companyTypeArrays)[pos],
                                                   getResources().getStringArray(R.array.companyTypeArrays)[21]};
                        hideFields();
                        break;
            }

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> compSubTypeAdapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_dropdown_item, subTypeList);

            // Specify the layout to use when the list of choices appears
            compSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            compSubTypeSpinner.setAdapter(compSubTypeAdapter);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

        private void hideFields() {
            hiddenCompTypeLayout.setVisibility(View.GONE);
            hiddenCompSubTypeLayout.setVisibility(View.GONE);
            custom_comp_type = false;
            custom_comp_sub_type = false;
        }
    }
}
