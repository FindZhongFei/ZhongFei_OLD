package com.fzhongfei.findzhongfei_final.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.activity.CompanyLoginActivity;
import com.fzhongfei.findzhongfei_final.activity.CompanyProfileActivity;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity1;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity2;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity3;
import com.fzhongfei.findzhongfei_final.activity.CompanySuccessfullyRegisteredActivity;
import com.fzhongfei.findzhongfei_final.activity.UserLoginActivity;
import com.fzhongfei.findzhongfei_final.activity.UserProfileActivity;
import com.fzhongfei.findzhongfei_final.activity.UserProfileEditActivity;
import com.fzhongfei.findzhongfei_final.activity.UserRegistrationActivity;
import com.fzhongfei.findzhongfei_final.activity.UserSignedInActivity;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.UserProfile;

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
                if(requestType.isEmpty())
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
                    JSONObject companyData = new JSONObject(String.valueOf(result.get("compData")));

                    HashMap<String, String> hashCompData = new HashMap<>();
                    hashCompData.put("compId", companyData.getString("comp_id"));
                    hashCompData.put("compName", companyData.getString("comp_name"));
                    hashCompData.put("compRepName", companyData.getString("comp_representative"));
                    hashCompData.put("compRepEmail", companyData.getString("comp_rep_email"));
                    hashCompData.put("compCeo", companyData.getString("comp_ceo"));
                    hashCompData.put("compLogoUrl", companyData.getString("comp_logo"));
                    hashCompData.put("compLicense", companyData.getString("comp_license"));
                    hashCompData.put("compPhone", companyData.getString("comp_phone"));
                    hashCompData.put("compEmail", companyData.getString("comp_email"));
                    hashCompData.put("compToken", companyData.getString("comp_token"));
                    hashCompData.put("compRegistration", companyData.getString("comp_reg_time"));
                    hashCompData.put("compStatus", companyData.getString("comp_status"));
                    hashCompData.put("compAddress1", companyData.getString("comp_addr_one"));
                    hashCompData.put("compAddress2", companyData.getString("comp_addr_two"));
                    hashCompData.put("compCity", companyData.getString("comp_city"));
                    hashCompData.put("compProvince", companyData.getString("comp_province"));
                    hashCompData.put("compType", companyData.getString("comp_type"));
                    hashCompData.put("compSubType", companyData.getString("comp_subtype"));
                    hashCompData.put("compDescription", companyData.getString("comp_desc"));
                    hashCompData.put("compWechatId", companyData.getString("comp_wechat"));

                    CompanyProfile companyProfile = new CompanyProfile(this.context);
                    companyProfile.initiateLogin(this.context);
                    companyProfile.SetCompData(this.context, hashCompData);

                    Intent intent = new Intent(this.context, CompanyProfileActivity.class);
                    ((Activity) context).finish();
                    context.getApplicationContext().startActivity(intent);
                }
                else if(requestType.equals("user_registration"))
                {
                    UserProfile userProfile = new UserProfile(this.context);
                    userProfile.initiateLogin(this.context);

                    Intent intent = new Intent(this.context, UserSignedInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ((Activity) context).finish();
                    context.getApplicationContext().startActivity(intent);
                }
                else if(requestType.equals("user_login"))
                {
                    JSONObject userData = new JSONObject(String.valueOf(result.get("userData")));

                    HashMap<String, String> hashUserData = new HashMap<>();
                    hashUserData.put("userId", userData.getString("user_id"));
                    hashUserData.put("userToken", userData.getString("user_token"));
                    hashUserData.put("userFirstName", userData.getString("user_fname"));
                    hashUserData.put("userLastName", userData.getString("user_sname"));
                    hashUserData.put("userEmail", userData.getString("user_email"));
                    hashUserData.put("userPhone", userData.getString("user_phone"));
                    hashUserData.put("userProfileUrl", userData.getString("user_profile"));

                    UserProfile userProfile = new UserProfile(this.context);
                    userProfile.initiateLogin(this.context);
                    userProfile.SetUserData(this.context, hashUserData);

                    Intent intent = new Intent(this.context, UserSignedInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ((Activity) context).finish();
                    context.getApplicationContext().startActivity(intent);
                }
                else if(requestType.equals("user_setProfilePicture"))
                {
                    String profileUrl = result.get("userProfileUrl").toString();

                    UserProfile userProfile = new UserProfile(context);
                    userProfile.setUserProfileUrl(context, profileUrl);
                    userProfile.setUserProfilePicture(context, Params.get("user_profile"));
                }
                else if(requestType.equals("requestCompLogo"))
                {
                    CompanyProfile companyProfile = new CompanyProfile(this.context);
                    companyProfile.setCompanyLogo(this.context, result.get("imageFile").toString());
                    Log.d(TAG, "onSuccess: Company Logo from result " + companyProfile.getCompanyLogo());
                    byte[] decodedLogo = Base64.decode(result.get("imageFile").toString(), Base64.DEFAULT);

                    CompanyProfileActivity.companyLogo.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
                    //TO DO: LOGO HASH AND VERIFYING LOGO
                }
                else if(requestType.equals("requestUserProfilePicture"))
                {
                    UserProfile userProfile = new UserProfile(this.context);
                    userProfile.setUserProfilePicture(this.context, result.get("imageFile").toString());
                    Log.d(TAG, "onSuccess: User Logo from result " + userProfile.getUserProfilePicture());
                    byte[] decodedLogo = Base64.decode(result.get("imageFile").toString(), Base64.DEFAULT);

                    UserProfileEditActivity.editProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
                    UserProfileActivity.profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
                    //TO DO: LOGO HASH AND VERIFYING LOGO
                }
                else 
                    {
                    Log.d(TAG, "onSuccess: Unknown Request type");
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
