package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.CompanyAdapter;
import com.fzhongfei.findzhongfei_final.adapter.MainFragmentCompaniesRecyclerView;
import com.fzhongfei.findzhongfei_final.adapter.SlideshowAdapter;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.model.trackRequestPosition;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment1 extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "MainFragment";
    Context mContext;

    // VIEWS
    View view;
    ViewPager mViewPager;
    SlideshowAdapter mSlideshowAdapter;
    LinearLayout slideshowDotsLayout;

    // VARIABLES
    private CompanyAdapter mCompanyAdapter; //arrayAdapter
    public static ArrayList<JSONObject> hashMapArrayList = new ArrayList<>();
    List<Companies> mCompaniesList; //List<cards> rowItems
    public static Companies company;

    // IMAGE SLIDER
    private int dotsCount;
    private ImageView[] dots;

    SharedPreferences companySharedPreferences;
    SharedPreferences userSharedPreferences;

    trackRequestPosition requestPosition ;
    public MainFragment1() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        mContext = getActivity();
        requestPosition = new trackRequestPosition(mContext);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main1, null);

        slideshowDotsLayout = view.findViewById(R.id.slide_show_dots);
        mViewPager = view.findViewById(R.id.main_fragment_view_pager);
        mSlideshowAdapter = new SlideshowAdapter(mContext);

        companySharedPreferences = this.getActivity().getSharedPreferences("companyPreference", 0);
        userSharedPreferences = this.getActivity().getSharedPreferences("userPreference", 0);

        setUpSlideshow();
        loadCompanies();

        mCompaniesList = new ArrayList<>();

        ArrayList<Companies> companiesArrayList = new ArrayList<>();

        JSONObject jsonObject;

        for(int i = 0; i < hashMapArrayList.size(); i++)
        {
            jsonObject = hashMapArrayList.get(i);

            company = new Companies(jsonObject.optInt("comp_id"),
                    jsonObject.optString("comp_logo"),
                    jsonObject.optString("comp_id"),
                    jsonObject.optString("comp_name"),
                    jsonObject.optString("comp_type"),
                    jsonObject.optString("comp_subtype"));
            companiesArrayList.add(i, company);

            requestImage(company);
        }
//
//        // REMOVE ANY DUPLICATE COMPANIES FROM LIST - 'LinkedHashSet' PRESERVES INSERTION ORDER AS WELL
        Set<Companies> nonDuplicatedCompanies = new LinkedHashSet<>(companiesArrayList);
        companiesArrayList.clear();
        companiesArrayList.addAll(nonDuplicatedCompanies);
        mCompaniesList.addAll(companiesArrayList);

        RecyclerView mainRecyclerView = view.findViewById(R.id.fragment_main_recycler_view);
        MainFragmentCompaniesRecyclerView mainFragmentCompaniesRecyclerView = new MainFragmentCompaniesRecyclerView(mContext, mCompaniesList);
        mainRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mainRecyclerView.setAdapter(mainFragmentCompaniesRecyclerView);
        mainRecyclerView.setNestedScrollingEnabled(false);

        mCompanyAdapter = new CompanyAdapter(mContext, R.layout.layout_item, mCompaniesList);
        mCompanyAdapter.notifyDataSetChanged();

        return view;
    }

    private void setUpSlideshow() {
        dotsCount = mSlideshowAdapter.getCount();
        dots = new ImageView[dotsCount];

        for(int i = 0; i < dotsCount; i++)
        {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.slide_show_dot_not_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);
            slideshowDotsLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.slide_show_dot_active));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 8000, 6000);

        mViewPager.setAdapter(mSlideshowAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < dotsCount; i++)
                {
                    if(dots[i] != null)
                    {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.slide_show_dot_not_active));
                    }
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.slide_show_dot_active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static String getIpAddress(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert manager != null;
        String ipAddress = Formatter.formatIpAddress(manager.getConnectionInfo().getIpAddress());;
        return ipAddress;
    }

    public static String getMacAddress(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert manager != null;
        String macAddress = Formatter.formatIpAddress(manager.getConnectionInfo().getIpAddress());;;
        return macAddress;
    }

    private void loadCompanies() {
        customStringRequest companiesRequest = new customStringRequest("comp/getComp.php");

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "tinView");
        Params.put("phone_serial_number", Build.SERIAL);
        Params.put("phone_model_number", Build.MODEL);
        Params.put("phone_id_number", Build.ID);
        Params.put("phone_manufacturer", Build.MANUFACTURER);
        Params.put("phone_brand", Build.BRAND);
        Params.put("phone_type", Build.TYPE);
        Params.put("phone_user", Build.USER);
        Params.put("phone_base", String.valueOf(Build.VERSION_CODES.BASE));
        Params.put("phone_sdk_version", String.valueOf(Build.VERSION.SDK_INT));
        Params.put("phone_host", Build.HOST);
        Params.put("phone_fingerprint", Build.FINGERPRINT);
        Params.put("phone_release", Build.VERSION.RELEASE);
        Params.put("phone_ip_address", getIpAddress(mContext));
        Params.put("phone_mac_address", getIpAddress(mContext));
        Params.put("startPosition",  Integer.toString(requestPosition.getCurrentPosition()));
        if(!companySharedPreferences.contains("companyIsLoggedIn") && !userSharedPreferences.contains("userIsLoggedIn"))
        {
            Params.put("is_loggedIn", "false");
            Params.put("host", "guest");
            Params.put("token", "none");
        }
        else if(companySharedPreferences.contains("companyIsLoggedIn"))
        {
            CompanyProfile companyProfile = new CompanyProfile(mContext);
            companyProfile.setPropertiesFromSharePreference(mContext);
            Params.put("is_loggedIn", "true");
            Params.put("host", "company");
            Params.put("token", companyProfile.getCompanyToken());
        }
        else if(userSharedPreferences.contains("userIsLoggedIn"))
        {
            UserProfile userProfile = new UserProfile(mContext);
            userProfile.setPropertiesFromSharePreference(mContext);
            Params.put("is_loggedIn", "true");
            Params.put("host", "user");
            Params.put("token", userProfile.getUserToken());
        }

        companiesRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("requestCompanies");

        companiesRequest.startConnection(mContext, callBack, Params);
    }

    private void requestImage(Companies companyItem) {
        mContext = getContext();
        customStringRequest imageRequest = new customStringRequest();
        HashMap<String, String> params = new HashMap<>();

        params.put("requestType", "requestCompLogo");
        params.put("logoUrl", companyItem.getImageUrl());
        params.put("compToken", null);
        params.put("host", "external");

        imageRequest.setUrlPath("comp/fetchImage.php");
        imageRequest.setParams(params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(params);
        callBack.SetRequestType("requestExternalCompLogo");

        imageRequest.startConnection(mContext, callBack, params);
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if(getActivity() != null)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch(mViewPager.getCurrentItem())
                        {
                            case 0:
                                mViewPager.setCurrentItem(1);
                                break;
                            case 1:
                                mViewPager.setCurrentItem(2);
                                break;
                            case 2:
                                mViewPager.setCurrentItem(3);
                                break;
                            case 3:
                                mViewPager.setCurrentItem(4);
                                break;
                            case 4:
                                mViewPager.setCurrentItem(5);
                                break;
                            case 5:
                                mViewPager.setCurrentItem(6);
                                break;
                            case 6:
                                mViewPager.setCurrentItem(7);
                                break;
                            case 7:
                                mViewPager.setCurrentItem(8);
                                break;
                            case 8:
                                mViewPager.setCurrentItem(9);
                                break;
                            case 9:
                                mViewPager.setCurrentItem(10);
                                break;
                            case 10:
                                mViewPager.setCurrentItem(11);
                                break;
                            case 11:
                                mViewPager.setCurrentItem(12);
                                break;
                            case 12:
                                mViewPager.setCurrentItem(13);
                                break;

                            default:
                                mViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}
