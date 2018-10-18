package com.fzhongfei.findzhongfei_final.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.activity.CompanyLoginActivity;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity1;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity2;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity3;
import com.fzhongfei.findzhongfei_final.activity.CompanySuccessfullyRegisteredActivity;
import com.fzhongfei.findzhongfei_final.activity.UserLoginActivity;
import com.fzhongfei.findzhongfei_final.activity.UserRegistrationActivity;
import com.fzhongfei.findzhongfei_final.activity.UserSignedInActivity;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class callBackImplement implements serverCallBack {

    public static final String TAG = "callBackImplement";
    private Context context;

    private boolean isSuccess = false;
    private String errorMessage = null;
    private String successMessage = null;
    private String requestType = null;

    private HashMap<String, String > Params = new HashMap<>();

    public callBackImplement(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(JSONObject result) {
        Log.d(TAG, "onSuccess: Call back result is " + result);
        try
        {
            String requestType = this.GetRequestType();
            this.errorMessage = result.get("errorMessage").toString();
            this.isSuccess = (boolean)result.get("isSuccess");
            this.successMessage = result.get("successMessage").toString();

            if(this.isSuccess || this.successMessage.equals ("success"))
            {
                if(requestType.equals(null))
                {
                    Toast.makeText(this.context, "Request type null", Toast.LENGTH_LONG).show();
                    //TO DO: SHOW ERROR DIVISION NOT TOAST
                }
                else if(requestType.equals("comp_registration"))
                {
                    String companyToken = result.get("comp_token").toString();
                    String pageNumber = result.get("process").toString();
                    switch (pageNumber)
                    {
                        case "page2":
                        {
                            Intent intent = new Intent(this.context, CompanyRegistrationActivity2.class);
                            intent.putExtra("comp_token", companyToken);
                            CompanyRegistrationActivity1.dialog.dismiss();
                            context.getApplicationContext().startActivity(intent);
                            ((Activity) context).finish();
                            break;
                        }
                        case "page3":
                        {
                            Intent intent = new Intent(this.context, CompanyRegistrationActivity3.class);
                            intent.putExtra("comp_token", companyToken);
                            CompanyRegistrationActivity2.dialog.dismiss();
                            context.getApplicationContext().startActivity(intent);
                            ((Activity) context).finish();
                            break;
                        }
                        case "final":
                        {
                            CompanyRegistrationActivity1.sCompanyProfile.initiateLogin(context.getApplicationContext());
                            Intent intent = new Intent(this.context, CompanySuccessfullyRegisteredActivity.class);
                            CompanyRegistrationActivity3.dialog.dismiss();
                            context.getApplicationContext().startActivity(intent);
                            ((Activity) context).finish();
                            break;
                        }
                        default:
                        {
                            context.getApplicationContext().startActivity(new Intent(this.context, CompanyRegistrationActivity1.class));
                            break;
                        }
                    }
                }
                else if(requestType.equals("comp_login"))
                {
                    Intent intent = new Intent(this.context, CompanyProfile.class);
                    intent.putExtra("isSignedIn", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.getApplicationContext().startActivity(intent);
                }
                else if(requestType.equals("user_registration"))
                {
                    Intent intent = new Intent(this.context, UserSignedInActivity.class);
                    UserLoginActivity.isLoggedIn = true;
                    intent.putExtra("isSignedIn", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.getApplicationContext().startActivity(intent);
                }
                else if(requestType.equals("user_login")) {
                    JSONObject jObject = new JSONObject(String.valueOf(result.get("userData")));

                    UserRegistrationActivity.sUserProfile.setUserId(jObject.getString("user_id"));
                    UserRegistrationActivity.sUserProfile.setUserToken(jObject.getString("user_token"));
                    UserRegistrationActivity.sUserProfile.setUserFirstName(jObject.getString("user_fname"));
                    UserRegistrationActivity.sUserProfile.setUserLastName(jObject.getString("user_sname"));
                    UserRegistrationActivity.sUserProfile.setUserEmail(jObject.getString("user_email"));
                    UserRegistrationActivity.sUserProfile.setUserPhone(jObject.getString("user_phone"));

                    Intent intent = new Intent(this.context, UserSignedInActivity.class);
                    boolean remember = UserLoginActivity.isLoggedIn = true;
                    intent.putExtra("isSignedIn", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.getApplicationContext().startActivity(intent);
                }
            }
            else
            {
                if(context.toString().contains("CompanyRegistrationActivity1"))
                {
                    ((CompanyRegistrationActivity1) context).stopCompanyRegistrationConnection1();
                }
                else if(context.toString().contains("CompanyRegistrationActivity2"))
                {
                    ((CompanyRegistrationActivity2) context).stopCompanyRegistrationConnection2();
                }
                else if(context.toString().contains("CompanyRegistrationActivity3"))
                {
                    ((CompanyRegistrationActivity3) context).stopCompanyRegistrationConnection3();
                }
                else if(context.toString().contains("CompanyLoginActivity"))
                {
                    ((CompanyLoginActivity) context).stopCompanyLoginConnection();
                }
                else if(context.toString().contains("UserLoginActivity"))
                {
                    ((UserLoginActivity) context).stopUserLoginConnection();
                }
                else if(context.toString().contains("UserRegistrationActivity"))
                {
                    ((UserRegistrationActivity) context).stopUserRegisterConnection();
                }


                Toast.makeText(this.context, this.errorMessage, Toast.LENGTH_LONG).show();

                Log.d(TAG, "onSuccess: errorMessage: " + this.errorMessage);
                Log.d(TAG, "onSuccess: successMessage: " + this.successMessage);
                Log.d(TAG, "onSuccess: isSuccess: " + this.isSuccess);
            }
        }
        catch (JSONException e)
        {
            this.errorMessage = e.getMessage();
        }
    }

    @Override
    public String getErrorMessage()
    {
        Log.d(TAG, "getErrorMessage: Error Message: " + this.errorMessage);
        return this.errorMessage;
    }

    @Override
    public String getSuccessMessage()
    {
        Log.d(TAG, "getSuccessMessage: success Message: " + this.successMessage);
        return this.successMessage;
    }

    @Override
    public boolean isSuccess()
    {
        Log.d(TAG, "isSuccess: is success::: " + this.isSuccess);
        return this.isSuccess;
    }

    public HashMap<String, String > getParams()
    {
        return this.Params;
    }
    public void setParams(HashMap<String,String> param)
    {
        this.Params = param;
    }

    @Override
    public void SetRequestType(String type)
    {
        this.requestType = type;
    }
    @Override
    public String GetRequestType()
    {
        return this.requestType;
    }
}
