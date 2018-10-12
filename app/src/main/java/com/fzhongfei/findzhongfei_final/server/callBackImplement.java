package com.fzhongfei.findzhongfei_final.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.activity.RegisterActivity1;
import com.fzhongfei.findzhongfei_final.activity.RegisterActivity2;
import com.fzhongfei.findzhongfei_final.activity.RegisterActivity3;
import com.fzhongfei.findzhongfei_final.activity.SuccessfullyRegisteredActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.fzhongfei.findzhongfei_final.activity.RegisterActivity1.stopConnection1;
import static com.fzhongfei.findzhongfei_final.activity.RegisterActivity2.stopConnection2;
import static com.fzhongfei.findzhongfei_final.activity.RegisterActivity3.stopConnection3;

public class callBackImplement implements serverCallBack {

    public static final String TAG = "callBackImplement";
    private Context context;

    private boolean isSuccess = false;
    private String errorMessage = null;
    private String successMessage = null;
    private HashMap<String, String > Params = new HashMap<>();
    public callBackImplement(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(JSONObject result) {
        Log.d(TAG, "onSuccess: Call back result is " + result);
        try {
            String companyToken, userToken, pageNumber;

            companyToken = result.get("comp_token").toString();
            userToken = result.get("user_token").toString();
            pageNumber = result.get("process").toString();
            this.errorMessage = result.get("errorMessage").toString();
            this.isSuccess = (boolean)result.get("isSuccess");
            this.successMessage = result.get("successMessage").toString();

            Log.d(TAG, "onSuccess: errorMessage : " + this.errorMessage);
            Log.d(TAG, "onSuccess: is Success : " + this.isSuccess);
            Log.d(TAG, "onSuccess: successMessage : " + this.successMessage);
            Log.d(TAG, "onSuccess: comp_token : " + companyToken);

            if(this.isSuccess || this.successMessage.equals ("success")) {
                switch (pageNumber) {
                    case "page2": {
                        Intent intent = new Intent(this.context, RegisterActivity2.class);
                        intent.putExtra("comp_token", companyToken);
                        RegisterActivity1.dialog.dismiss();
                        context.getApplicationContext().startActivity(intent);
                        ((Activity) context).finish();
                        break;
                    }
                    case "page3": {
                        Intent intent = new Intent(this.context, RegisterActivity3.class);
                        intent.putExtra("comp_token", companyToken);
                        RegisterActivity2.dialog.dismiss();
                        context.getApplicationContext().startActivity(intent);
                        ((Activity) context).finish();
                        break;
                    }
                    case "final": {
                        Intent intent = new Intent(this.context, SuccessfullyRegisteredActivity.class);
                        RegisterActivity3.dialog.dismiss();
                        context.getApplicationContext().startActivity(intent);
                        ((Activity) context).finish();
                        break;
                    }
                    default: {
                        context.getApplicationContext().startActivity(new Intent(this.context, RegisterActivity1.class));
                        break;
                    }
                }
            } else {
                if(context.toString().contains("RegisterActivity1")) {
                    stopConnection1();
                } else if(context.toString().contains("RegisterActivity2")) {
                    stopConnection2();
                } else if(context.toString().contains("RegisterActivity3")) {
                    stopConnection3();
                }

                Toast.makeText(this.context, this.errorMessage, Toast.LENGTH_LONG).show();

                Log.d(TAG, "onSuccess: errorMessage: " + this.errorMessage);
                Log.d(TAG, "onSuccess: successMessage: " + this.successMessage);
                Log.d(TAG, "onSuccess: isSuccess: " + this.isSuccess);
            }
        } catch (JSONException e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Override
    public String getErrorMessage() {
        Log.d(TAG, "getErrorMessage: Error Message: " + this.errorMessage);
        return this.errorMessage;
    }

    @Override
    public String getSuccessMessage() {
        Log.d(TAG, "getSuccessMessage: success Message: " + this.successMessage);
        return this.successMessage;
    }

    @Override
    public boolean isSuccess() {
        Log.d(TAG, "isSuccess: is success::: " + this.isSuccess);
        return this.isSuccess;
    }

    public HashMap<String, String > getParams() {
        return this.Params;
    }
    public void setParams(HashMap<String,String> param) {
        this.Params = param;
    }
}
