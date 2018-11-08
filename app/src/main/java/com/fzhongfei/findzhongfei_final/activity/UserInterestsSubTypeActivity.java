package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInterestsSubTypeActivity extends AppCompatActivity implements View.OnClickListener {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserInterestsSubTypeAct";
    Context mContext = UserInterestsSubTypeActivity.this;

    // VIEWS

    // VARIABLES
    ArrayList<String> subInterests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interests_sub_type);

        Log.d(TAG, "onCreate: Running...");

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserInterests(subInterests);
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
    private void submitUserInterests(ArrayList<String> interests) {
        customStringRequest registerRequest = new customStringRequest("user/interests.php");

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "user_subInterests");
        for(int i = 0; i < interests.size(); i++)
        {
            Params.put("user_subInterests", interests.get(i));
        }

        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_subInterests");
        registerRequest.startConnection(mContext, callBack, Params);
    }
}
