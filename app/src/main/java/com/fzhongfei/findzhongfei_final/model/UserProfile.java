package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserProfile {
    private String userId, userToken, userFirstName, userLastName, userEmail, userPhone, userProfileUrl, userProfilePicture;
    private SharedPreferences sharedPreference;
    private boolean isLoggedIn, profilePictureIsDownloaded;

    public UserProfile(Context context)
    {
        sharedPreference = context.getSharedPreferences( "userPreference", 0);

        this.isLoggedIn = sharedPreference.contains("userIsLoggedIn");
    }

    private void setSharedPreference(Context context, String keyName, String value, int valueType)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPreference", 0);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.apply();

        switch (valueType)
        {
            case 1:
                sharedEditor.putBoolean(keyName, Boolean.getBoolean(value));
                sharedEditor.apply();
                break;
            case 2:
                sharedEditor.putFloat(keyName, Float.parseFloat(value));
                sharedEditor.apply();
                break;
            case 3:
                sharedEditor.putInt(keyName, Integer.parseInt(value));
                sharedEditor.apply();
                break;
            case 4:
                sharedEditor.putLong(keyName, Long.parseLong(value));
                sharedEditor.apply();
                break;
            default:
                sharedEditor.putString(keyName, value);
                sharedEditor.apply();
                break;
        }
    }

    public void clearSharedPreference(Context context) {
        profilePictureIsDownloaded = false;
        SharedPreferences.Editor editor = this.sharedPreference.edit();
        editor.clear();
        editor.apply();
    }

    public void initiateLogin(Context context)
    {
        this.setSharedPreference(context, "userIsLoggedIn",  "true", 1);
    }

    public void setPropertiesFromSharePreference(Context context)
    {
        setUserId(context, sharedPreference.getString("user_userId",null));
        setUserToken(context, sharedPreference.getString("user_token",null));
        setUserFirstName(context, sharedPreference.getString("user_firstName",null));
        setUserLastName(context, sharedPreference.getString("user_lastName",null));
        setUserEmail(context, sharedPreference.getString("user_email",null));
        setUserPhone(context, sharedPreference.getString("user_phone",null));
    }

    public void SetUserData(Context context, HashMap<String, String> userData)
    {
        setUserId(context, userData.get("userId"));
        setUserToken(context, userData.get("userToken"));
        setUserFirstName(context, userData.get("userFirstName"));
        setUserLastName(context, userData.get("userLastName"));
        setUserEmail(context, userData.get("userEmail"));
        setUserPhone(context, userData.get("userPhone"));
    }

    public void setUserProfileUrl(Context context, String userProfileUrl)
    {
        this.profilePictureIsDownloaded = true;
        this.userProfileUrl = userProfileUrl;
        this.setSharedPreference(context, "user_userProfileUrl", userProfileUrl, 0);
    }
    public void setUserProfilePicture(Context context, String userProfilePicture)
    {
        this.userProfilePicture = userProfilePicture;
        this.setSharedPreference(context, "user_userProfileUrl", userProfilePicture, 0);
    }
    public void setUserId(Context context, String userId)
    {
        this.userId = userId;
        this.setSharedPreference(context, "user_userId", userId, 0);
    }
    public void setUserToken(Context context, String userToken)
    {
        this.userToken = userToken;
        this.setSharedPreference(context, "user_token", userToken, 0);
    }
    public void setUserFirstName(Context context, String userFirstName)
    {
        this.userFirstName = userFirstName;
        this.setSharedPreference(context, "user_firstName", userFirstName, 0);
    }
    public void setUserLastName(Context context, String userLastName)
    {
        this.userLastName = userLastName;
        this.setSharedPreference(context, "user_lastName", userLastName, 0);
    }
    public void setUserEmail(Context context, String userEmail)
    {
        this.userEmail = userEmail;
        this.setSharedPreference(context, "user_email", userEmail, 0);
    }
    public void setUserPhone(Context context, String userPhone)
    {
        this.userPhone = userPhone;
        this.setSharedPreference(context, "user_phone", userPhone, 0);
    }

    public boolean isUserProfileDownloaded() {
        return profilePictureIsDownloaded;
    }
    public String getUserProfileUrl() {
        return userProfileUrl;
    }
    public String getUserProfilePicture() {
        return userProfilePicture;
    }
    public String getUserId()
    {
        return userId;
    }
    public String getUserToken()
    {
        return userToken;
    }
    public String getUserFirstName()
    {
        return userFirstName;
    }
    public String getUserLastName()
    {
        return userLastName;
    }
    public String getUserEmail()
    {
        return userEmail;
    }
    public String getUserPhone()
    {
        return userPhone;
    }
    public boolean getUserIsLoggedIn()
    {
        return this.isLoggedIn;
    }
}
