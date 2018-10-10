package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesCompany {
    private static final String PREF_COMPANY_EMAIL = "comp_email";
    private static final String PREF_COMPANY_NAME = "comp_name";

    private static SharedPreferences getSharedPreferences(Context mContext) {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static void setSharedPreferenceValue(Context mContext, String name, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getSharedPreferenceValue(Context mContext, String name) {
        return getSharedPreferences(mContext).getString(name, "");
    }

    private static void clearCompanyInformation(Context mContext) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.clear();
        editor.commit();
    }

    public static void setCompanyEmail(Context mContext, String companyEmail) {
        setSharedPreferenceValue(mContext, PREF_COMPANY_EMAIL, companyEmail);
    }

    public static String getCompanyEmail(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_COMPANY_EMAIL, "");
    }
}
