package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.HashMap;

public class CompanyProfileActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "CompanyProfileActivity";
    private Context mContext = CompanyProfileActivity.this;

    // VIEWS
    TextView    companyName, companyType, companySubType, companyProvince, companyCity, companyPhone, companyEmail,
                companyCeo, companyRepresentative, companyRepresentativeEmail, companyAddress1, companyAddress2,
                companyWechatId, companyDescription;

    public static ImageView   companyLogo;
    CompanyProfile companyProfile;
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
        companyProfile = new CompanyProfile(mContext);
        companyProfile.setPropertiesFromSharePreference(mContext);
        if(companyProfile.getIsLoggedIn())
        {
            // INITIALIZING VIEWS
            companyLogo = findViewById(R.id.company_profile_logo);
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
            companyWechatId = findViewById(R.id.TextViewWechatId);
            companyDescription = findViewById(R.id.TextViewDescription);

            showCompanyProfile();
        }
        else
            startActivity(new Intent(mContext, CompanyLoginActivity.class));


    }

    // SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;

        mToolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    // UI - BACK BUTTON
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
    // UI - TOOLBAR ITEMS CLICKED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // MENU ITEM CLICK HANDLING
        if(id == R.id.profile_settings) {
            startActivity(new Intent(mContext, UserSettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // ALL COMPANY PROFILE FIELDS
    private void showCompanyProfile() {
        if(this.companyProfile.getIsLoggedIn())
        {
            customStringRequest imageRequest = new customStringRequest();

            String companyNameValue = this.companyProfile.getCompanyName();
            String companyPhoneValue = this.companyProfile.getCompanyPhone();
            String companyEmailValue = this.companyProfile.getCompanyEmail();
            String companyCeoValue = this.companyProfile.getCompanyCeo();
            String companyRepresentativeValue = this.companyProfile.getCompanyRepresentative();
            String companyRepresentativeEmailValue = this.companyProfile.getCompanyRepresentativeEmail();
            String companyAddress1Value = this.companyProfile.getCompanyAddress1();
            String companyAddress2Value = this.companyProfile.getCompanyAddress2();
            String companyCityValue = this.companyProfile.getCompanyCity();
            String companyProvinceValue = this.companyProfile.getCompanyProvince();
            String companyTypeValue = this.companyProfile.getCompanyType();
            String companySubTypeValue = this.companyProfile.getCompanySubType();
            String companyWechatIdValue = this.companyProfile.getCompanyWechatId();
            String companyDescriptionValue = this.companyProfile.getCompanyDescription();
            String companyLogoUrl = this.companyProfile.getCompanyLogoUrl();
            String companyToken = this.companyProfile.getCompanyToken();
            String companyLogoValue = this.companyProfile.getCompanyLogo();
            HashMap<String,String> params = new HashMap();
            params.put("requestType", "requestCompLogo");
            params.put("logoUrl", companyLogoUrl);
            params.put("compToken", companyToken);

            imageRequest.setUrlPath("comp/fetchImage.php");
            imageRequest.setParams(params);

            callBackImplement callBack = new callBackImplement(mContext);
            callBack.setParams(params);
            callBack.SetRequestType("requestCompLogo");

            imageRequest.startConnection(mContext, callBack, params);

            companyName.setText(companyNameValue);
            companyPhone.setText(companyPhoneValue);
            companyEmail.setText(companyEmailValue);
            companyCeo.setText(companyCeoValue);
            companyRepresentative.setText(companyRepresentativeValue);
            companyRepresentativeEmail.setText(companyRepresentativeEmailValue);
            companyAddress1.setText(companyAddress1Value);
            companyAddress2.setText(companyAddress2Value);
            companyCity.setText(companyCityValue);
            companyProvince.setText(companyProvinceValue);
            companyType.setText(companyTypeValue);
            companySubType.setText(companySubTypeValue);
            companyWechatId.setText(companyWechatIdValue);
            companyDescription.setText(companyDescriptionValue);
            Log.d(TAG, "showCompanyProfile: COMPANY LOGO BASE64 "+companyLogoValue);
        }
        else
        {
            Toast.makeText(mContext, "Please login", Toast.LENGTH_LONG).show();
            startActivity(new Intent(mContext,CompanyLoginActivity.class));
        }

    }
}
