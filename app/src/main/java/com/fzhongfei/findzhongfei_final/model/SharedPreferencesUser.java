package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUser {
    public static final String PREF_USER_EMAIL = "user_email";
    public static final String PREF_USER_USERNAME = "user_username";
    public static final String PREF_USER_PHONE = "user_phone";

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

    public static void clearUserInformation(Context mContext) {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.clear();
        editor.apply();
    }

    public static void setUserEmail(Context mContext, String userEmail) {
        setSharedPreferenceValue(mContext, PREF_USER_EMAIL, userEmail);
    }

    public static String getUserEmail(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_EMAIL, "");
    }

    public static void setUsername(Context mContext, String username) {
        setSharedPreferenceValue(mContext, PREF_USER_USERNAME, username);
    }

    public static String getUsername(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_USERNAME, "");
    }

    public static void setPhoneNumber(Context mContext, String userPhone) {
        setSharedPreferenceValue(mContext, PREF_USER_PHONE, userPhone);
    }

    public static String getPhoneNumber(Context mContext) {
        return getSharedPreferences(mContext).getString(PREF_USER_PHONE, "");
    }
}
