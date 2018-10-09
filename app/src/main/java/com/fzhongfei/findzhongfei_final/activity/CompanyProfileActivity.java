package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;

public class CompanyProfileActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "CompanyProfileActivity";
    private Context mContext = CompanyProfileActivity.this;

    // VIEWS
    TextView    companyName, companyType, companySubType, companyProvince, companyCity, companyPhone, companyEmail,
                companyCeo, companyRepresentative, companyRepresentativeEmail, companyAddress1, companyAddress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        Log.d(TAG, "onCreate: Running...");

        // UI - FULLSCREEN NO TOOLBAR
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        companyName = findViewById(R.id.TextViewCompName);
        companyType = findViewById(R.id.TextViewCompType);
        companySubType = findViewById(R.id.TextViewCompSubType);
        companyProvince = findViewById(R.id.TextViewProvince);
        companyCity = findViewById(R.id.TextViewCity);
        companyPhone = findViewById(R.id.TextViewCompPhone);
        companyEmail = findViewById(R.id.TextViewCompEmail);
        companyCeo = findViewById(R.id.TextViewCeo);
        companyRepresentative = findViewById(R.id.TextViewRepresentative);
        companyRepresentativeEmail = findViewById(R.id.TextViewRepEmail);
        companyAddress1 = findViewById(R.id.TextViewAddress1);
        companyAddress2 = findViewById(R.id.TextViewAddress2);

        showCompanyProfile();
    }

    // SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;

        mToolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // BACK BUTTON PRESSED
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // UI - SETTINGS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATING THE MENU
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // MENU ITEM CLICK HANDLING
        if(id == R.id.profile_settings) {
            startActivity(new Intent(mContext, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // ALL COMPANY PROFILE FIELDS
    private void showCompanyProfile() {
        companyName.setText(RegisterActivity1.getCompanyFields()[0]);
        companyPhone.setText(RegisterActivity1.getCompanyFields()[1]);
        companyEmail.setText(RegisterActivity1.getCompanyFields()[2]);
        companyCeo.setText(RegisterActivity1.getCompanyFields()[3]);
        companyRepresentative.setText(RegisterActivity1.getCompanyFields()[4]);
        companyRepresentativeEmail.setText(RegisterActivity1.getCompanyFields()[5]);
        companyAddress1.setText(RegisterActivity2.getCompanyFields()[0]);
        companyAddress2.setText(RegisterActivity2.getCompanyFields()[1]);
        companyCity.setText(RegisterActivity2.getCompanyFields()[2]);
        companyProvince.setText(RegisterActivity2.getCompanyFields()[3]);
        companyType.setText(RegisterActivity3.getCompanyFields()[0]);
        companySubType.setText(RegisterActivity3.getCompanyFields()[1]);
//        compWechatId.setText(RegisterActivity3.getCompanyFields()[2]);
    }
}
