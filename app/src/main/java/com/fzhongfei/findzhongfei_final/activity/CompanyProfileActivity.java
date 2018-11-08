package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView    companyName, companyType, companySubType, companyProvince, companyCity, companyPhone, companyEmail,
                        companyCeo, companyRepresentative, companyRepresentativeEmail, companyAddress1, companyAddress2,
                        companyWechatId, companyDescription;
    private TextView    companyNameTitle, companyTypeTitle, companySubTypeTitle, companyProvinceTitle, companyCityTitle, companyPhoneTitle,
                        companyEmailTitle, companyCeoTitle, companyRepresentativeTitle, companyRepresentativeEmailTitle, companyAddress1Title,
                        companyAddress2Title, companyWechatIdTitle, companyDescriptionTitle;
    public static ImageView companyLogo;

    private Menu menu;
    private CompanyProfile companyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        Log.d(TAG, "onCreate: Running...");

        // UI - FULLSCREEN NO TOOLBAR
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // UI - TOOLBAR
        setUpActivityToolbar();

        companyProfile = new CompanyProfile(mContext);
        companyProfile.setPropertiesFromSharePreference(mContext);

        if(companyProfile.getCompanyIsLoggedIn())
        {
            // INITIALIZING VIEWS
            LinearLayout compTypeLayout = findViewById(R.id.comp_type_linear_layout);
            LinearLayout compSubTypeLayout = findViewById(R.id.comp_sub_type_linear_layout);
            LinearLayout compProvinceLayout = findViewById(R.id.comp_province_linear_layout);
            LinearLayout compCityLayout = findViewById(R.id.comp_city_linear_layout);
            LinearLayout compPhoneLayout = findViewById(R.id.comp_phone_linear_layout);
            LinearLayout compWechatLayout = findViewById(R.id.comp_wechat_linear_layout);
            LinearLayout compEmailLayout = findViewById(R.id.comp_email_linear_layout);
            LinearLayout compCeoLayout = findViewById(R.id.comp_ceo_linear_layout);
            LinearLayout compRepNameLayout = findViewById(R.id.comp_rep_name_linear_layout);
            LinearLayout compRepEmailLayout = findViewById(R.id.comp_rep_email_linear_layout);
            LinearLayout compAddress1Layout = findViewById(R.id.comp_address1_linear_layout);
            LinearLayout compAddress2Layout = findViewById(R.id.comp_address2_linear_layout);
            LinearLayout compDescLayout = findViewById(R.id.comp_desc_linear_layout);

            companyTypeTitle = findViewById(R.id.compTypeTitle);
            companySubTypeTitle = findViewById(R.id.compSubTypeTitle);
            companyProvinceTitle = findViewById(R.id.provinceTitle);
            companyCityTitle = findViewById(R.id.cityTitle);
            companyPhoneTitle = findViewById(R.id.compPhoneTitle);
            companyWechatIdTitle  = findViewById(R.id.wechatIdTitle);
            companyEmailTitle = findViewById(R.id.compEmailTitle);
            companyCeoTitle = findViewById(R.id.ceoTitle);
            companyRepresentativeTitle = findViewById(R.id.representativeTitle);
            companyRepresentativeEmailTitle = findViewById(R.id.repEmailTitle);
            companyAddress1Title = findViewById(R.id.address1Title);
            companyAddress2Title = findViewById(R.id.address2Title);
            companyDescriptionTitle= findViewById(R.id.descTitle);

            companyLogo = findViewById(R.id.company_profile_logo);
            companyName = findViewById(R.id.TextViewCompName);
            companyType = findViewById(R.id.TextViewCompType);
            companySubType = findViewById(R.id.TextViewCompSubType);
            companyProvince = findViewById(R.id.TextViewProvince);
            companyCity = findViewById(R.id.TextViewCity);
            companyPhone = findViewById(R.id.TextViewCompPhone);
            companyWechatId = findViewById(R.id.TextViewWechatId);
            companyEmail = findViewById(R.id.TextViewCompEmail);
            companyCeo = findViewById(R.id.TextViewCeo);
            companyRepresentative = findViewById(R.id.TextViewRepresentative);
            companyRepresentativeEmail = findViewById(R.id.TextViewRepEmail);
            companyAddress1 = findViewById(R.id.TextViewAddress1);
            companyAddress2 = findViewById(R.id.TextViewAddress2);
            companyDescription = findViewById(R.id.TextViewDescription);

            compTypeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyTypeTitle, companyType, "companyType");
                }
            });
            compSubTypeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companySubTypeTitle, companySubType, "companySubType");
                }
            });
            compProvinceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyProvinceTitle, companyProvince, "companyProvince");
                }
            });
            compCityLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyCityTitle, companyCity, "companyCity");
                }
            });
            compPhoneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyPhoneTitle, companyPhone, "companyPhone");
                }
            });
            compWechatLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyWechatIdTitle, companyWechatId, "companyWechatID");
                }
            });
            compEmailLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyEmailTitle, companyEmail, "companyEmail");
                }
            });
            compCeoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyCeoTitle, companyCeo, "companyCeo");
                }
            });
            compRepNameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyRepresentativeTitle, companyRepresentative, "companyRepName");
                }
            });
            compRepEmailLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyRepresentativeEmailTitle, companyRepresentativeEmail, "companyRepEmail");
                }
            });
            compAddress1Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyAddress1Title, companyAddress1, "companyAddress1");
                }
            });
            compAddress2Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyAddress2Title, companyAddress2, "companyAddress2");
                }
            });
            compDescLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(companyDescriptionTitle, companyDescription, "companyDesc");
                }
            });

            showCompanyProfile();
        }
        else
        {
            startActivity(new Intent(mContext, CompanyLoginActivity.class));
            finish();
        }
    }

    // SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.company_profile_collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.company_profile_app_bar);

        mToolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // HIDE AND SHOW FAB ACCORDING TO COLLAPSING TOOLBAR
        FloatingActionButton fab = findViewById(R.id.company_profile_action_bar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                updateProfile(companyName, companyName, "companyName");
            }
        });

        mToolbar.setTitleMarginStart(-70);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingToolbarLayout.setTitle(companyProfile.getCompanyName());
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    // Collapsed
                    collapsingToolbarLayout.setTitle(companyProfile.getCompanyName());
                    companyName.setVisibility(View.GONE);
                }
                else
                {
                    // Expanded
                    collapsingToolbarLayout.setTitle("");
                    companyName.setVisibility(View.VISIBLE);
                }

                if(scrollRange == -1)
                {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0)
                {
                    isShow = true;
                    toggleEditIcon(true);
                }
                else if(isShow)
                {
                    isShow = false;
                    toggleEditIcon(false);
                }
            }
        });
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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        toggleEditIcon(false);

        return true;
    }

    // UI - TOOLBAR ITEMS CLICKED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // MENU ITEM CLICK HANDLING
        if(id == R.id.profile_settings)
        {
            startActivity(new Intent(mContext, UserSettingsActivity.class));
        }
        else if(id == R.id.edit_company_profile)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // HIDE EDIT ICON WHEN APPBAR IS EXPANDED
    private void toggleEditIcon(boolean show) {
        if(menu != null)
        {
            MenuItem item = menu.findItem(R.id.edit_company_profile);
            item.setVisible(show);
        }
    }

    // ALL COMPANY PROFILE FIELDS
    private void showCompanyProfile() {
        if(this.companyProfile.getCompanyIsLoggedIn())
        {
            customStringRequest imageRequest = new customStringRequest();

            String companyTokenValue = this.companyProfile.getCompanyToken();
            String companyLogoUrl = this.companyProfile.getCompanyLogoUrl();
            String companyLogoValue = this.companyProfile.getCompanyLogo();
            Boolean logoIsDownloaded = this.companyProfile.isCompanyLogoDownloaded();

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

            if(!logoIsDownloaded || companyLogoValue == null)
            {
                HashMap<String, String> params = new HashMap<>();

                params.put("requestType", "requestCompLogo");
                params.put("logoUrl", companyLogoUrl);
                params.put("compToken", companyTokenValue);

                imageRequest.setUrlPath("comp/fetchImage.php");
                imageRequest.setParams(params);

                callBackImplement callBack = new callBackImplement(mContext);
                callBack.setParams(params);
                callBack.SetRequestType("requestCompLogo");

                imageRequest.startConnection(mContext, callBack, params);
            }
            else
            {
                byte[] decodedLogo = Base64.decode(companyLogoValue, Base64.DEFAULT);
                companyLogo.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
            }

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
            Log.d(TAG, "showCompanyProfile: COMPANY LOGO BASE64 " + companyLogoValue);
        }
        else
        {
            Toast.makeText(mContext, "Please login", Toast.LENGTH_LONG).show();
            startActivity(new Intent(mContext,CompanyLoginActivity.class));
        }
    }

    // UPDATE PROFILE FIELDS
    private void updateProfile(final TextView title, final TextView fieldToEdit, final String updateField) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Edit");
        builder.setMessage(title.getText());

        final EditText input = new EditText(mContext);
        input.setHint(fieldToEdit.getText());
        builder.setView(input);

        // POSITIVE BUTTON
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedText = input.getText().toString();

                customStringRequest editRequest = new customStringRequest();
                HashMap<String, String> params = new HashMap<>();

                params.put("requestType", "updateCompanyProfile");
                params.put("updateField", updateField);
                params.put("updateValue", updatedText);
                params.put("companyToken", companyProfile.getCompanyToken());
                params.put("companyId", companyProfile.getCompanyId());


                editRequest.setUrlPath("comp/update.php");
                editRequest.setParams(params);

                callBackImplement callBack = new callBackImplement(mContext);
                callBack.setParams(params);
                callBack.SetRequestType("updateCompanyInfo");
                callBack.setFieldTextView(fieldToEdit);
                editRequest.startConnection(mContext, callBack, params);

            }
        });

        // NEGATIVE BUTTON
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }
}
