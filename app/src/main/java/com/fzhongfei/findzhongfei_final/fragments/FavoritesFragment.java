package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.CompanyAdapter;
import com.fzhongfei.findzhongfei_final.model.Company;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "FavoritesFragment";
    Context mContext;

    // VIEWS
    View view;
    RecyclerView mRecyclerView;
    CompanyAdapter mAdapter;
    List<Company> mCompanyList;


    public FavoritesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        mContext = getActivity();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, null);
        setupRecyclerView();

        return view;
    }

    // UI - SETTING UP FAVORITES
    private void setupRecyclerView() {
        mCompanyList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.favRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_10,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_14,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_9,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_15,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_2,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_3,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mCompanyList.add(new Company(1,
                R.drawable.img_wall_12,
                getString(R.string.universityNameValue),
                getString(R.string.university),
                getString(R.string.universityDescValue),
                Integer.parseInt(getString(R.string.three))));

        mAdapter = new CompanyAdapter(mContext, mCompanyList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
