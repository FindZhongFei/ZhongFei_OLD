package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInterestsActivity extends AppCompatActivity implements View.OnClickListener {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserInterestsActivity";
    private Context mContext = UserInterestsActivity.this;

    // VIEWS
//    private TextView interest_counter;
    private Button submitButton;

    UserProfile mUserProfile;

    // VARIABLES
    public static ArrayList<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interests);

        Log.d(TAG, "onCreate: Running");

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        TextView    mImage1_1, mImage1_2, mImage1_3,
                    mImage2_1, mImage2_2, mImage2_3,
                    mImage3_1, mImage3_2, mImage3_3;

        // INITIALIZING VIEWS
//        interest_counter = findViewById(R.id.interest_counter);
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interests.size() < 3)
                {
                    Toast.makeText(mContext, "Please select at least 3 types", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    submitUserInterests(interests);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Drawable clickedImage = getResources().getDrawable(R.drawable.background_highlight_image);
        int numberOfInterests = interests.size();
        TextView textViewValue = (TextView) v;
        String interestsMinimumLimit;
        String imageText = textViewValue.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(textViewValue.getForeground() == null)
            {
//                textViewValue.setBackground(new ColorDrawable(mContext.getColor(R.color.colorGreen)));
                textViewValue.setHighlightColor(getColor(R.color.colorGreen));
                textViewValue.setForeground(clickedImage);

                interests.add(imageText);
                for (int i = 0; i < interests.size(); i++)
                {
                    //Append all the values to a string
                    numberOfInterests = i + 1;
                    interestsMinimumLimit = numberOfInterests + " / 3";
                    submitButton.setText(interestsMinimumLimit);

                    if(i > 1)
                    {
                        submitButton.setText(getResources().getString(R.string.next));
                    }
                }
            }
            else if(textViewValue.getForeground() != null)
            {
                numberOfInterests -= 1;
                interestsMinimumLimit = numberOfInterests + " / 3";
                submitButton.setText(interestsMinimumLimit);

                if(numberOfInterests >= 3)
                {
                    submitButton.setText(getResources().getString(R.string.next));
                }

                interests.remove(textViewValue.getText().toString());
                textViewValue.setForeground(null);
            }
        }
    }

    // SUBMIT INTERESTS
    private void submitUserInterests(ArrayList<String> interests) {
        Intent intent = new Intent(mContext, UserInterestsSubTypeActivity.class);
        intent.putStringArrayListExtra("user_interests", interests);
        startActivity(intent);

//        customStringRequest registerRequest = new customStringRequest("user/interests.php");
//
//        HashMap<String, String> Params = new HashMap<>();
//
//        Params.put("action", "user_interests");
//        Params.put("user_token", mUserProfile.getUserToken());
//        for(int i = 0; i < interests.size(); i++)
//        {
//            Params.put("user_interests", interests.toString());
//        }
//        registerRequest.setParams(Params);
//
//        callBackImplement callBack = new callBackImplement(mContext);
//        callBack.setParams(Params);
//        callBack.SetRequestType("user_interests");
//        registerRequest.startConnection(mContext, callBack, Params);
    }
}
