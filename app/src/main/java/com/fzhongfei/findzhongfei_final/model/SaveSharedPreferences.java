package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {

    // USER DETAILS
    public static final String PREF_USER_FIRST_NAME = "user_firstName";
    public static final String PREF_USER_LAST_NAME = "user_lastName";
    public static final String PREF_USER_EMAIL = "user_email";
    public static final String PREF_USER_PHONE = "user_phone";

    // COMPANY DETAILS
    public static final String PREF_COMPANY_NAME = "comp_name";
    public static final String PREF_COMPANY_TYPE = "comp_type";
    public static final String PREF_COMPANY_SUB_TYPE = "comp_subType";
    public static final String PREF_COMPANY_PROVINCE = "comp_province";
    public static final String PREF_COMPANY_CITY = "comp_city";
    public static final String PREF_COMPANY_PHONE = "comp_phone";
    public static final String PREF_COMPANY_EMAIL = "comp_email";
    public static final String PREF_COMPANY_CEO = "comp_ceo";
    public static final String PREF_COMPANY_REPRESENTATIVE = "comp_representative";
    public static final String PREF_COMPANY_REPRESENTATIVE_EMAIL = "comp_representative_email";
    public static final String PREF_COMPANY_ADDRESS_1 = "comp_address1";
    public static final String PREF_COMPANY_ADDRESS_2 = "comp_address2";

    private static SharedPreferences getSharedPreferences(Context mContext) {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static void setSharedPreferenceValue(Context mContext, String name, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreferenceValue(Context mContext, String name) {
        return getSharedPreferences(mContext).getString(name, "");
    }

    public static void clearSharedInformation(Context mContext) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.clear();
        editor.apply();
    }

    // USER - SETTER AND GETTERS
    public static void setUserFirstName(Context mContext, String userFirstName) {
        setSharedPreferenceValue(mContext, PREF_USER_FIRST_NAME, userFirstName);
    }
    public static String getUserFirstName(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_FIRST_NAME, "");
    }

    public static void setUserLastName(Context mContext, String userLastName) {
        setSharedPreferenceValue(mContext, PREF_USER_LAST_NAME, userLastName);
    }
    public static String getUserLastName(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_LAST_NAME, "");
    }

    public static void setUserEmail(Context mContext, String userEmail) {
        setSharedPreferenceValue(mContext, PREF_USER_EMAIL, userEmail);
    }
    public static String getUserEmail(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserPhone(Context mContext, String userPhone) {
        setSharedPreferenceValue(mContext, PREF_USER_PHONE, userPhone);
    }
    public static String getUserPhone(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_PHONE, "");
    }

    // COMPANY - SETTERS AND GETTERS
    public static void setCompanyName(Context mContext, String companyName) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_NAME, companyName);
    }
    public static String getCompanyName(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_NAME, "");
    }

    public static void setCompanyType(Context mContext, String companyType) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_TYPE, companyType);
    }
    public static String getCompanyType(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_TYPE, "");
    }

    public static void setCompanySubType(Context mContext, String companySubType) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_SUB_TYPE, companySubType);
    }
    public static String getCompanySubType(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_SUB_TYPE, "");
    }

    public static void setCompanyProvince(Context mContext, String companyProvince) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_PROVINCE, companyProvince);
    }
    public static String getCompanyProvince(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_PROVINCE, "");
    }

    public static void setCompanyCity(Context mContext, String companySubType) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_CITY, companySubType);
    }
    public static String getCompanyCity(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_CITY, "");
    }

    public static void setCompanyPhone(Context mContext, String companyPhone) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_PHONE, companyPhone);
    }
    public static String getCompanyPhone(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_PHONE, "");
    }

    public static void setCompanyEmail(Context mContext, String companyEmail) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_EMAIL, companyEmail);
    }
    public static String getCompanyEmail(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_EMAIL, "");
    }

    public static void setCompanyCeo(Context mContext, String companyCeo) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_CEO, companyCeo);
    }
    public static String getCompanyCeo(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_CEO, "");
    }

    public static void setCompanyRepresentative(Context mContext, String companyRepresentative) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_REPRESENTATIVE, companyRepresentative);
    }
    public static String getCompanyRepresentative(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_REPRESENTATIVE, "");
    }

    public static void setCompanyRepresentativeEmail(Context mContext, String companyRepresentativeEmail) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_REPRESENTATIVE_EMAIL, companyRepresentativeEmail);
    }
    public static String getCompanyRepresentativeEmail(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_REPRESENTATIVE_EMAIL, "");
    }

    public static void setCompanyAddress1(Context mContext, String companyAddress1) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_ADDRESS_1, companyAddress1);
    }
    public static String getCompanyAddress1(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_ADDRESS_1, "");
    }

    public static void setCompanyAddress2(Context mContext, String companyAddress2) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_ADDRESS_2, companyAddress2);
    }
    public static String getCompanyAddress2(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_ADDRESS_2, "");
    }
}
