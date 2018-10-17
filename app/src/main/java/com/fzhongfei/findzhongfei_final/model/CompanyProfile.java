package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class CompanyProfile {
    private String companyId, companyToken, companyLogo, companyLicense, companyName, companyType, companySubType, companyCity,
            companyProvince, companyPhone, companyEmail, companyCeo, companyRepresentative,
            companyRepresentativeEmail, companyAddress1, companyAddress2, companyWechatId, companyDescription;
    private boolean isLoggedIn = false;

    public CompanyProfile(Context context)
    {
        SharedPreferences sharedPreference = context.getSharedPreferences( "companyPreference", 0);

        this.isLoggedIn = sharedPreference.contains("isLoggedIn");
    }

    public CompanyProfile(String companyId, String companyToken, String companyLogo, String companyLicense,
                          String companyName, String companyType, String companySubType, String companyCity,
                          String companyProvince, String companyPhone, String companyEmail, String companyCeo,
                          String companyRepresentative, String companyRepresentativeEmail, String companyAddress1,
                          String companyAddress2, String companyWechatId, String companyDescription)
    {
        this.companyId = companyId;
        this.companyToken = companyToken;
        this.companyLogo = companyLogo;
        this.companyLicense = companyLicense;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companySubType = companySubType;
        this.companyCity = companyCity;
        this.companyProvince = companyProvince;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.companyCeo = companyCeo;
        this.companyRepresentative = companyRepresentative;
        this.companyRepresentativeEmail = companyRepresentativeEmail;
        this.companyAddress1 = companyAddress1;
        this.companyAddress2 = companyAddress2;
        this.companyWechatId = companyWechatId;
        this.companyDescription = companyDescription;
    }

    private void setSharedPreference(Context context, String keyName, String value, int valueType)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("companyPreference",0);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
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

    public void initiateLogin(Context context)
    {
        this.setSharedPreference(context, "isLoggedIn",  "true", 1);
    }

    public void SetCompData(HashMap<String, String> compData)
    {
        this.companyId = compData.get("compId");
        this.companyToken = compData.get("compToken");
        this.companyLogo = compData.get("compLogo");
        this.companyLicense = compData.get("compLicense");
        this.companyName = compData.get("compName");
        this.companyType = compData.get("compType");
        this.companySubType = compData.get("compSubType");
        this.companyProvince = compData.get("compProvince");
        this.companyPhone = compData.get("compPhone");
        this.companyCeo = compData.get("compCeo");
        this.companyEmail = compData.get("compEmail");
        this.companyRepresentative = compData.get("compRepName");
        this.companyRepresentativeEmail = compData.get("compRepEmail");
        this.companyAddress1 = compData.get("compAddress1");
        this.companyAddress2 = compData.get("compAddress2");
        this.companyWechatId = compData.get("compWechatId");
        this.companyDescription = compData.get("compDescription");
    }

    public void setCompanyName(Context context, String companyName)
    {
        this.companyName = companyName;
        this.setSharedPreference(context, "comp_name", companyName, 0);
    }
    public void setCompanyType(Context context, String companyType)
    {
        this.companyType = companyType;
        this.setSharedPreference(context, "comp_type", companyType, 0);

    }
    public void setCompanySubType(Context context, String companySubType)
    {
        this.companySubType = companySubType;
        this.setSharedPreference(context, "comp_subType", companySubType, 0);
    }
    public void setCompanyProvince(Context context, String companyProvince)
    {
        this.companyProvince = companyProvince;
        this.setSharedPreference(context, "comp_province", companyProvince, 0);
    }
    public void setCompanyCity(Context context, String companyCity)
    {
        this.companyCity = companyCity;
        this.setSharedPreference(context, "comp_city", companyCity, 0);

    }
    public void setCompanyPhone(Context context, String companyPhone)
    {
        this.companyPhone = companyPhone;
        this.setSharedPreference(context, "comp_phone", companyPhone, 4);
    }
    public void setCompanyEmail(Context context, String companyEmail)
    {
        this.companyEmail = companyEmail;
        this.setSharedPreference(context, "comp_email", companyEmail, 0);
    }
    public void setCompanyCeo(Context context, String companyCeo)
    {
        this.companyCeo = companyCeo;
        this.setSharedPreference(context, "comp_ceo", companyCeo, 0);
    }
    public void setCompanyRepresentative(Context context, String companyRepresentative)
    {
        this.companyRepresentative = companyRepresentative;
        this.setSharedPreference(context, "comp_representative", companyRepresentative, 0);
    }
    public void setCompanyRepresentativeEmail(Context context, String companyRepresentativeEmail)
    {
        this.companyRepresentativeEmail = companyRepresentativeEmail;
        this.setSharedPreference(context, "comp_representativeEmail", companyRepresentativeEmail, 0);
    }
    public void setCompanyAddress1(Context context, String companyAddress1)
    {
        this.companyAddress1 = companyAddress1;
        this.setSharedPreference(context, "comp_address1", companyAddress1, 0);
    }
    public void setCompanyAddress2(Context context, String companyAddress2)
    {
        this.companyAddress2 = companyAddress2;
        this.setSharedPreference(context, "comp_address2", companyAddress2, 0);
    }
    public void setCompanyWechatId(Context context, String companyWechatId)
    {
        this.companyWechatId = companyWechatId;
        this.setSharedPreference(context, "comp_wechat", companyWechatId, 0);
    }
    public void setCompanyDescription(Context context, String companyDescription)
    {
        this.companyDescription = companyDescription;
        this.setSharedPreference(context, "comp_description", companyDescription, 0);
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public String getCompanyType() {
        return companyType;
    }
    public String getCompanySubType()
    {
        return companySubType;
    }
    public String getCompanyProvince()
    {
        return companyProvince;
    }
    public String getCompanyCity()
    {
        return companyCity;
    }
    public String getCompanyPhone()
    {
        return companyPhone;
    }
    public String getCompanyEmail()
    {
        return companyEmail;
    }
    public String getCompanyCeo()
    {
        return companyCeo;
    }
    public String getCompanyRepresentative()
    {
        return companyRepresentative;
    }
    public String getCompanyRepresentativeEmail()
    {
        return companyRepresentativeEmail;
    }
    public String getCompanyAddress1()
    {
        return companyAddress1;
    }
    public String getCompanyAddress2()
    {
        return companyAddress2;
    }
    public String getCompanyWechatId()
    {
        return companyWechatId;
    }
    public String getCompanyDescription()
    {
        return companyDescription;
    }
    public boolean getIsLoggedIn()
    {
        return this.isLoggedIn;
    }
}
