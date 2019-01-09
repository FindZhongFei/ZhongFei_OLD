package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.utils.BounceTouchListener;

public class ClickedCompany extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "ClickedCompany";
    private Context mContext = ClickedCompany.this;

    // VIEWS
    private TextView    companyName, companyType, companySubType, companyProvince, companyCity, companyPhone, companyEmail,
                        companyCeo, companyRepresentative, companyRepresentativeEmail, companyAddress1, companyAddress2,
                        companyWechatId, companyDescription;
    public ImageView companyLogo;
    public Button sendCompanyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_company);
        Log.d(TAG, "onCreate: Running...");

        final SharedPreferences companySharedPreferences = getSharedPreferences("companyPreference", 0);
        final SharedPreferences userSharedPreferences = getSharedPreferences("userPreference", 0);

        Intent intent = getIntent();
        final String compNameIntent = intent.getStringExtra("CompanyName");
        final String companyTypeIntent = intent.getStringExtra("CompanyType");
//        String compImageIntent = intent.getStringExtra("CompanyImage");
        final String compSubtypeIntent = intent.getStringExtra("CompanySubtype");
        final String compIdIntent = intent.getStringExtra("CompanyId");
        final String compToken = intent.getStringExtra("CompanyToken");

        companyLogo = findViewById(R.id.clicked_company_image_view);
        companyName = findViewById(R.id.clicked_company_name);
        companyType = findViewById(R.id.clicked_company_type);
        companySubType = findViewById(R.id.clicked_company_sub_type);
        companyProvince = findViewById(R.id.clicked_company_province);
        companyCity = findViewById(R.id.clicked_company_city);
        companyPhone = findViewById(R.id.clicked_company_phone);
        companyEmail = findViewById(R.id.clicked_company_email);
        companyCeo = findViewById(R.id.clicked_company_ceo);
        companyRepresentative = findViewById(R.id.clicked_company_representative);
        companyRepresentativeEmail = findViewById(R.id.clicked_company_rep_email);
        companyAddress1 = findViewById(R.id.clicked_company_address1);
        companyAddress2 = findViewById(R.id.clicked_company_address2);
        companyWechatId = findViewById(R.id.clicked_company_wechat_id);
        companyDescription = findViewById(R.id.clicked_company_description);
        sendCompanyMessage = findViewById(R.id.send_company_message);

        sendCompanyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userSharedPreferences.contains("userIsLoggedIn") || companySharedPreferences.contains("companyIsLoggedIn"))
                {
                    Intent intent = new Intent(mContext, ChatComposeActivity.class);
                    intent.putExtra("partnerName", compNameIntent);
                    intent.putExtra("partnerToken", compToken);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(mContext, "Please login first", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mContext, UserSignedInActivity.class));
                }
            }
        });

//        byte[] decodedLogo = Base64.decode(compImageIntent, Base64.DEFAULT);
//        companyLogo.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));

        Bitmap bitmap = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("CompanyImage"),0, getIntent().getByteArrayExtra("CompanyImage").length);
        companyLogo.setImageBitmap(bitmap);

        companyName.setText(compNameIntent);
        companyType.setText(companyTypeIntent);
        companySubType.setText(compSubtypeIntent);

        ScrollView scrollView = findViewById(R.id.clicked_company_scroll_view);
        final RelativeLayout relativeLayout = findViewById(R.id.clicked_company_image_view_layout);

        BounceTouchListener bounceTouchListener = BounceTouchListener.create(scrollView, R.id.clicked_company_linear_layout,
                new BounceTouchListener.OnTranslateListener() {
                    @Override
                    public void onTranslate(float translation) {
                        if (translation > 0) {
                            float scale = ((3 * translation) / relativeLayout.getMeasuredHeight()) + 1;
                            relativeLayout.setScaleX(scale);
                            relativeLayout.setScaleY(scale);
                        }
                    }
                }
        );

        scrollView.setOnTouchListener(bounceTouchListener);
    }
}
