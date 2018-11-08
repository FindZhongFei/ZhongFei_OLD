package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class CompanyProfile {

    private String  companyId, companyToken, companyLogo, companyLicense, companyName, companyType, companySubType, companyCity,
                    companyProvince, companyPhone, companyEmail, companyCeo, companyRepresentative,
                    companyRepresentativeEmail, companyAddress1, companyAddress2, companyWechatId, companyDescription,
                    companyRegistrationTime, companyStatus, companyLogoUrl;
    private SharedPreferences sharedPreference;
    private boolean isLoggedIn, compLogoIsDownloaded;

    public CompanyProfile(Context context)
    {
        sharedPreference = context.getSharedPreferences( "companyPreference", 0);

        this.isLoggedIn = sharedPreference.contains("companyIsLoggedIn");
    }

    private void setSharedPreference(Context context, String keyName, String value, int valueType)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("companyPreference",0);
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

    public void setAbstractField(Context context, String fieldKey, String fieldValue)
    {
        this.setSharedPreference(context, fieldKey, fieldValue, 0);
    }

    public void clearSharedPreference(Context context)
    {
        compLogoIsDownloaded = false;
        SharedPreferences.Editor editor = this.sharedPreference.edit();
        editor.clear();
        editor.apply();
    }

    public void initiateLogin(Context context)
    {
        this.setSharedPreference(context, "companyIsLoggedIn",  "true", 1);
    }

    public void setPropertiesFromSharePreference(Context context)
    {
        setCompanyId(context, sharedPreference.getString("comp_id",null));
        setCompanyToken(context, sharedPreference.getString("comp_token",null));
        setCompanyLogo(context, sharedPreference.getString("comp_logo",null));
        setCompanyLicense(context, sharedPreference.getString("comp_license",null));
        setCompanyName(context, sharedPreference.getString("comp_name",null));
        setCompanyType(context, sharedPreference.getString("comp_type",null));
        setCompanySubType(context, sharedPreference.getString("comp_subType",null));
        setCompanyProvince(context, sharedPreference.getString("comp_province",null));
        setCompanyCity(context, sharedPreference.getString("comp_city",null));
        setCompanyPhone(context,  String.valueOf(sharedPreference.getString("comp_phone",null)));
        setCompanyEmail(context, sharedPreference.getString("comp_email",null));
        setCompanyCeo(context, sharedPreference.getString("comp_ceo",null));
        setCompanyRepresentative(context, sharedPreference.getString("comp_representative",null));
        setCompanyRepresentativeEmail(context, sharedPreference.getString("comp_representativeEmail",null));
        setCompanyAddress1(context, sharedPreference.getString("comp_address1",null));
        setCompanyAddress2(context, sharedPreference.getString("comp_address2",null));
        setCompanyWechatId(context, sharedPreference.getString("comp_wechat",null));
        setCompanyDescription(context, sharedPreference.getString("comp_description",null));
        setCompanyRegistrationTime(context, sharedPreference.getString("comp_registrationTime",null));
        setCompanyStatus(context, sharedPreference.getString("comp_status",null));
        setCompanyLogoUrl(context, sharedPreference.getString("comp_logoUrl", null));
    }

    public void SetCompData(Context context, HashMap<String, String> compData)
    {
        setCompanyId(context, compData.get("compId"));
        setCompanyToken(context,compData.get("compToken"));
        setCompanyLogo(context,compData.get("compLogo"));
        setCompanyLogoUrl(context,compData.get("compLogoUrl"));
        setCompanyLicense(context,compData.get("compLicense"));
        setCompanyName(context,compData.get("compName"));
        setCompanyType(context,compData.get("compType"));
        setCompanySubType(context,compData.get("compSubType"));
        setCompanyProvince(context,compData.get("compProvince"));
        setCompanyCity(context,compData.get("compCity"));
        setCompanyPhone(context,compData.get("compPhone"));
        setCompanyEmail(context,compData.get("compEmail"));
        setCompanyCeo(context,compData.get("compCeo"));
        setCompanyRepresentative(context,compData.get("compRepName"));
        setCompanyRepresentativeEmail(context,compData.get("compRepEmail"));
        setCompanyAddress1(context,compData.get("compAddress1"));
        setCompanyAddress2(context,compData.get("compAddress2"));
        setCompanyWechatId(context,compData.get("compWechatId"));
        setCompanyDescription(context,compData.get("compDescription"));
        setCompanyRegistrationTime(context,compData.get("compRegistration"));
        setCompanyStatus(context,compData.get("compStatus"));
    }

    public void setCompanyId(Context context, String companyId)
    {
        this.companyId = companyId;
        this.setSharedPreference(context, "comp_id", companyId, 0);
    }
    public void setCompanyLogoUrl(Context context, String companyLogoUrl)
    {
        this.companyLogoUrl = companyLogoUrl;
        this.setSharedPreference(context, "comp_logoUrl", companyLogoUrl, 0);
    }
    public void setCompanyLogo(Context context, String companyLogo)
    {
        this.compLogoIsDownloaded = true;
        this.companyLogo = companyLogo;
        this.setSharedPreference(context, "comp_logo", companyLogo, 0);
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
        this.setSharedPreference(context, "comp_phone", companyPhone, 0);
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
    public void setCompanyLicense(Context context, String companyLicense)
    {
        this.companyLicense = companyLicense;
        this.setSharedPreference(context, "comp_license", companyLicense, 0);
    }
    public void setCompanyToken(Context context, String companyToken)
    {
        this.companyToken = companyToken;
        this.setSharedPreference(context, "comp_token", companyToken, 0);
    }
    public void setCompanyRegistrationTime(Context context, String companyRegistrationTime)
    {
        this.companyRegistrationTime = companyRegistrationTime;
        this.setSharedPreference(context, "comp_registrationTime", companyRegistrationTime, 0);
    }
    public void setCompanyStatus(Context context, String companyStatus)
    {
        this.companyStatus = companyStatus;
        this.setSharedPreference(context, "comp_status", companyStatus, 0);
    }

    public boolean isCompanyLogoDownloaded() {
        return compLogoIsDownloaded;
    }
    public String getCompanyId()
    {
        return companyId;
    }
    public  String getCompanyLogoUrl() {
        return companyLogoUrl;
    }
    public String getCompanyLogo()
    {
        return companyLogo;
    }
    public String getCompanyLicense()
    {
        return companyLicense;
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
    public String getCompanyToken()
    {
        return companyToken;
    }
    public boolean getCompanyIsLoggedIn()
    {
        return this.isLoggedIn;
    }
}
