package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.ChatListAdapter;
import com.fzhongfei.findzhongfei_final.helper.SimpleDividerItemDecoration;
import com.fzhongfei.findzhongfei_final.model.ChatList;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "ChatFragment";
    Context mContext;

    // VIEWS
    View view;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    public static RecyclerView recyclerView;

    // VARIABLES
    public static ChatListAdapter mAdapter;
    public static ArrayList<ChatList> chatRoomArrayList;

    private UserProfile mUserProfile;
    private CompanyProfile mCompanyProfile;

    SharedPreferences companySharedPreferences;
    SharedPreferences userSharedPreferences;

    public ChatFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        view = inflater.inflate(R.layout.fragment_chat, null);

        mContext = getActivity();
        mUserProfile = new UserProfile(mContext);
        assert mContext != null;
        companySharedPreferences = mContext.getSharedPreferences("companyPreference", 0);
        userSharedPreferences = mContext.getSharedPreferences("userPreference", 0);

        // INITIALIZING VIEWS
        recyclerView = view.findViewById(R.id.chat_recycler_view);
        chatRoomArrayList = new ArrayList<>();
        mAdapter = new ChatListAdapter(mContext, chatRoomArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // SwipeRefreshLayout
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_chat_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /*
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                fetchMessages();
            }
        });

        if(!companySharedPreferences.contains("companyIsLoggedIn") && !userSharedPreferences.contains("userIsLoggedIn"))
        {
            Toast.makeText(mContext, "Please log in first", Toast.LENGTH_SHORT).show();
        }
        else if(companySharedPreferences.contains("companyIsLoggedIn"))
        {
            mCompanyProfile = new CompanyProfile(mContext);
            mCompanyProfile.setPropertiesFromSharePreference(mContext);
        }
        else if(userSharedPreferences.contains("userIsLoggedIn"))
        {

            mUserProfile.setPropertiesFromSharePreference(mContext);

            fetchMessages();
        }

        return view;
    }

    private void fetchMessages() {
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
        fetchMessages();
    }
}
