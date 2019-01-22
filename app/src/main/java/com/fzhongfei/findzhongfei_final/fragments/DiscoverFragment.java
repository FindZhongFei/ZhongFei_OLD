package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.ChatListAdapter;
import com.fzhongfei.findzhongfei_final.model.ChatList;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "DiscoverFragment";
    Context mContext;

    // VIEWS
    View view;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    public static RecyclerView recyclerView;

    // VARIABLES
    public static ChatListAdapter mAdapter;
    public static ArrayList<ChatList> discoverArrayList;

    private UserProfile mUserProfile;
    private CompanyProfile mCompanyProfile;

    SharedPreferences companySharedPreferences;
    SharedPreferences userSharedPreferences;

    public DiscoverFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        view = inflater.inflate(R.layout.fragment_discover, null);

        mContext = getActivity();

        if(mContext != null)
        {
            mUserProfile = new UserProfile(mContext);
            mCompanyProfile = new CompanyProfile(mContext);

            companySharedPreferences = mContext.getSharedPreferences("companyPreference", 0);
            userSharedPreferences = mContext.getSharedPreferences("userPreference", 0);
        }

        // SwipeRefreshLayout
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_discover_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fetchStatus();
            }
        });

        return view;
    }

    private void fetchStatus() {
        customStringRequest companiesRequest = new customStringRequest("message/history.php");

        HashMap<String, String> Params = new HashMap<>();

        Params.put("reqType", "history");
        if(userSharedPreferences.contains("userIsLoggedIn"))
        {
            Params.put("hostType", "user");
            Params.put("hostToken", mUserProfile.getUserToken());
        }
        else if(companySharedPreferences.contains("companyIsLoggedIn"))
        {
            Params.put("hostType", "company");
            Params.put("hostToken", mCompanyProfile.getCompanyToken());
        }

        companiesRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("requestChats");

        companiesRequest.startConnection(mContext, callBack, Params);
    }

    @Override
    public void onRefresh() {
        fetchStatus();
    }
}
