package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchedCompanies {

    private static final String TAG = "FetchedCompanies";
    
    private static SharedPreferences sharedPreference;
    private static ArrayList<Companies> companiesArrayList = new ArrayList<>();
    private static final String NUM_COMPANIES = "num_companies";
    private static final String SHARED_PREFERENCE = "savedCompanies";
    private static final String COMP_ID_INTEGER = "comp_id_int";
    private static final String COMP_ID_String = "comp_id";
    private static final String COMP_LOGO_URL = "comp_logo";
    private static final String COMP_NAME = "comp_name";
    private static final String COMP_TYPE = "comp_type";
    private static final String COMP_SUB_TYPE = "comp_subtype";
    private static final String COMP_IMAGE = "logo_val";

    public static void saveCompanyData(Context context, JSONArray companyData) throws JSONException {
        sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = sharedPreference.edit();

        Companies company;
        // TO KNOW HOW MANY COMPANIES ARE SAVED AND ITERATE
        editor.putInt(NUM_COMPANIES, companyData.length());

        for(int i = 0; i < companyData.length(); i++)
        {
            JSONObject retrievedData = companyData.getJSONObject(i);

            company = new Companies(
                    retrievedData.getInt("comp_id"),
                    retrievedData.getString("comp_logo"),
                    retrievedData.getString("comp_id"),
                    retrievedData.getString("comp_name"),
                    retrievedData.getString("comp_type"),
                    retrievedData.getString("comp_subtype"),
                    retrievedData.getString("logo_val")
            );

            editor.putInt(COMP_ID_INTEGER + "_" + i, company.getId());
            editor.putString(COMP_ID_String + "_" + i, company.getCompId());
            editor.putString(COMP_LOGO_URL + "_" + i, company.getImageUrl());
            editor.putString(COMP_NAME + "_" + i, company.getCompName());
            editor.putString(COMP_TYPE + "_" + i, company.getCompType());
            editor.putString(COMP_SUB_TYPE + "_" + i, company.getCompSubType());
            editor.putString(COMP_IMAGE + "_" + i, company.getImageLogo());

            editor.apply();
            editor.commit();
        }

        Log.d(TAG, "getSavedCompanyData: dkfjdkfjkdjfkj" + sharedPreference.getInt(NUM_COMPANIES, 0));
    }

    public static ArrayList<Companies> getSavedCompanyData(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE, 0);

        Log.d(TAG, "getSavedCompanyData: dkfjdkfjkdjfkj" + sharedPreference.getInt(NUM_COMPANIES, 0));

        for(int i = 0; i < sharedPreference.getInt(NUM_COMPANIES, 0); i++)
        {
            Companies company = new Companies(
                    sharedPreference.getInt(COMP_ID_INTEGER + "_" + i, 0),
                    sharedPreference.getString(COMP_LOGO_URL + "_" + i, ""),
                    sharedPreference.getString(COMP_ID_String + "_" + i, ""),
                    sharedPreference.getString(COMP_NAME + "_" + i, ""),
                    sharedPreference.getString(COMP_TYPE + "_" + i, ""),
                    sharedPreference.getString(COMP_SUB_TYPE + "_" + i, ""),
                    sharedPreference.getString(COMP_IMAGE + "_" + i, "")
            );

            companiesArrayList.add(0, company);
        }

        return companiesArrayList;
    }
}
