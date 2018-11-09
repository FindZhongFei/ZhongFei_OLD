package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInterestsSubTypeActivity extends AppCompatActivity implements View.OnClickListener {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserInterestsSubTypeAct";
    Context mContext = UserInterestsSubTypeActivity.this;

    // VIEWS

    // VARIABLES
    ArrayList<String> interests = new ArrayList<>();
    ArrayList<String> subInterests = new ArrayList<>();

    UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interests_sub_type);

        Log.d(TAG, "onCreate: Running...");

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        TextView mImage1_1, mImage1_2, mImage1_3,
                mImage2_1, mImage2_2, mImage2_3,
                mImage3_1, mImage3_2, mImage3_3;
        Button submitButton;

        // INITIALIZING VIEWS
        mImage1_1 = findViewById(R.id.image_1_1_interest);
        mImage1_2 = findViewById(R.id.image_1_2_interest);
        mImage1_3 = findViewById(R.id.image_1_3_interest);
        mImage2_1 = findViewById(R.id.image_2_1_interest);
        mImage2_2 = findViewById(R.id.image_2_2_interest);
        mImage2_3 = findViewById(R.id.image_2_3_interest);
        mImage3_1 = findViewById(R.id.image_3_1_interest);
        mImage3_2 = findViewById(R.id.image_3_2_interest);
        mImage3_3 = findViewById(R.id.image_3_3_interest);
        submitButton = findViewById(R.id.user_interests_submit_button);

        mImage1_1.setOnClickListener(this);
        mImage1_2.setOnClickListener(this);
        mImage1_3.setOnClickListener(this);
        mImage2_1.setOnClickListener(this);
        mImage2_2.setOnClickListener(this);
        mImage2_3.setOnClickListener(this);
        mImage3_1.setOnClickListener(this);
        mImage3_2.setOnClickListener(this);
        mImage3_3.setOnClickListener(this);

        mImage1_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage1_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage1_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);

        // ACTIVITY STUFF
        interests = getIntent().getStringArrayListExtra("user_interests");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserInterests();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Drawable clickedImage = getResources().getDrawable(R.drawable.background_highlight_image);
        TextView textViewValue = (TextView) v;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(textViewValue.getForeground() == null)
            {
//                textViewValue.setBackground(new ColorDrawable(mContext.getColor(R.color.colorGreen)));
                textViewValue.setHighlightColor(getColor(R.color.colorGreen));
                textViewValue.setForeground(clickedImage);

                String imageText = textViewValue.getText().toString();
                subInterests.add(imageText);
            }
            else if(textViewValue.getForeground() != null)
            {
                subInterests.remove(textViewValue.getText().toString());
                textViewValue.setForeground(null);
            }
        }
    }

    // SUBMIT SUB INTERESTS
    private void submitUserInterests() {
        JSONArray interestsJsonArray = new JSONArray(interests);
        JSONArray subInterestsJsonArray = new JSONArray(subInterests);

        customStringRequest registerRequest = new customStringRequest("user/interests.php");

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "user_interests");
        Params.put("user_token", mUserProfile.getUserToken());
        String strInterest = "";
        String strSubString = "";
        for(int i = 0; i < interests.size(); i++)
        {

            try {
//                Params.put("user_interests[]", interestsJsonArray.get(i).toString());
                if(strInterest.isEmpty())
                    strInterest.concat(subInterestsJsonArray.get(i).toString());
                else
                    strInterest.concat(","+subInterestsJsonArray.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < subInterests.size(); i++)
        {
            try {

                if(strInterest.isEmpty())
                    strSubString.concat(interestsJsonArray.get(i).toString());
                else
                    strSubString.concat(","+interestsJsonArray.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Params.put("user_interests",  strInterest);
        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_interests");
        registerRequest.startConnection(mContext, callBack, Params);
    }
}
