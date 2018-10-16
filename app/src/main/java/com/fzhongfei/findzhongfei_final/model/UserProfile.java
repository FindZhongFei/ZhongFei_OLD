package com.fzhongfei.findzhongfei_final.model;

import java.util.HashMap;

public class UserProfile {
    private String userId, userToken, userFirstName, userLastName, userEmail, userPhone;

    public UserProfile() {}

    public UserProfile(String userId, String userToken, String userFirstName, String userLastName,
                          String userEmail, String userPhone)
    {
        this.userId = userId;
        this.userToken = userToken;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public void SetUserData(HashMap<String, String> userData)
    {
        this.userId = userData.get("userId");
        this.userToken = userData.get("userToken");
        this.userFirstName = userData.get("userFirstName");
        this.userLastName = userData.get("userLastName");
        this.userEmail = userData.get("userEmail");
        this.userPhone = userData.get("userPhone");
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public void setUserToken(String userToken)
    {
        this.userToken = userToken;
    }
    public void setUserFirstName(String userFirstName)
    {
        this.userFirstName = userFirstName;
    }
    public void setUserLastName(String userLastName)
    {
        this.userLastName = userLastName;
    }
    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
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
}
