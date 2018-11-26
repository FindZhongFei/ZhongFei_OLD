package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.FavoriteCompanyAdapter;
import com.fzhongfei.findzhongfei_final.adapter.ILoadMore;
import com.fzhongfei.findzhongfei_final.model.FavoriteCompany;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "FavoritesFragment";
    Context mContext;

    // VIEWS
    View view;
    RecyclerView mRecyclerView;
    FavoriteCompanyAdapter mAdapter;
    List<FavoriteCompany> mFavoriteCompanyList = new ArrayList<>();

    String compId;

    public FavoritesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        mContext = getActivity();
        compId = new CompanyProfile(mContext).getCompanyId();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, null);

        mRecyclerView = view.findViewById(R.id.favRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new FavoriteCompanyAdapter(mRecyclerView, mContext, mFavoriteCompanyList);
        mRecyclerView.setAdapter(mAdapter);
        setupRecyclerView();

        // LOAD MORE EVENT
        mAdapter.setILoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(mFavoriteCompanyList.size() <= 50)
                {
                    mFavoriteCompanyList.add(null);
                    mAdapter.notifyItemInserted(mFavoriteCompanyList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mFavoriteCompanyList.remove(mFavoriteCompanyList.size() - 1);
                            mAdapter.notifyItemRemoved(mFavoriteCompanyList.size());

                            // LOAD MORE
                            int index = mFavoriteCompanyList.size();
                            int end = index + 1;

                            for(int i = index; i < end; i++)
                            {
                                mFavoriteCompanyList.add(new FavoriteCompany(1,
                                        R.drawable.img_wall_10, compId,
                                        "HELLOOOOOOOOOOOOOOOOOOOOo",
                                        getString(R.string.university),
                                        getString(R.string.universityDescValue),
                                        Integer.parseInt(getString(R.string.three))));
                            }

                            mAdapter.notifyDataSetChanged();
                            mAdapter.setLoading();
                        }
                    }, 5000);
                }
                else
                {
                    Toast.makeText(mContext, "That's all!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // UI - SETTING UP FAVORITES
    private void setupRecyclerView() {
        if(mContext != null)
        {
            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_10, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_14, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_9, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_15, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_2, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_3, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));

            mFavoriteCompanyList.add(new FavoriteCompany(1,
                    R.drawable.img_wall_12, compId,
                    getString(R.string.universityNameValue),
                    getString(R.string.university),
                    getString(R.string.universityDescValue),
                    Integer.parseInt(getString(R.string.three))));
        }
    }
}
