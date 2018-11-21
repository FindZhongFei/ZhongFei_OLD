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

import java.util.ArrayList;

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
                    mImage3_1, mImage3_2, mImage3_3,
                    mImage4_1, mImage4_2, mImage4_3,
                    mImage5_1, mImage5_2, mImage5_3,
                    mImage6_1, mImage6_2, mImage6_3,
                    mImage7_1, mImage7_2, mImage7_3;

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
        mImage4_1 = findViewById(R.id.image_4_1_interest);
        mImage4_2 = findViewById(R.id.image_4_2_interest);
        mImage4_3 = findViewById(R.id.image_4_3_interest);
        mImage5_1 = findViewById(R.id.image_5_1_interest);
        mImage5_2 = findViewById(R.id.image_5_2_interest);
        mImage5_3 = findViewById(R.id.image_5_3_interest);
        mImage5_1 = findViewById(R.id.image_5_1_interest);
        mImage5_2 = findViewById(R.id.image_5_2_interest);
        mImage5_3 = findViewById(R.id.image_5_3_interest);
        mImage6_1 = findViewById(R.id.image_6_1_interest);
        mImage6_2 = findViewById(R.id.image_6_2_interest);
        mImage6_3 = findViewById(R.id.image_6_3_interest);
        mImage7_1 = findViewById(R.id.image_7_1_interest);
        mImage7_2 = findViewById(R.id.image_7_2_interest);
        mImage7_3 = findViewById(R.id.image_7_3_interest);
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
        mImage4_1.setOnClickListener(this);
        mImage4_2.setOnClickListener(this);
        mImage4_3.setOnClickListener(this);
        mImage5_1.setOnClickListener(this);
        mImage5_2.setOnClickListener(this);
        mImage5_3.setOnClickListener(this);
        mImage6_1.setOnClickListener(this);
        mImage6_2.setOnClickListener(this);
        mImage6_3.setOnClickListener(this);
        mImage7_1.setOnClickListener(this);
        mImage7_2.setOnClickListener(this);
        mImage7_3.setOnClickListener(this);

        mImage1_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage1_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage1_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage2_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage3_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage4_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage4_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage4_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage5_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage5_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage5_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage6_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage6_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage6_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage7_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage7_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);
        mImage7_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.img_wall_12), null, null, null);

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
        startActivity(new Intent(mContext, UserInterestsSubTypeActivity.class).putStringArrayListExtra("user_interests", interests));
    }
}
