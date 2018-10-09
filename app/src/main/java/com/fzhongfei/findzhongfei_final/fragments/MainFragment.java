package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fzhongfei.findzhongfei_final.R;

public class MainFragment extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "MainFragment";
    Context mContext;

    // VIEWS
    View view;

    public MainFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        mContext = getActivity();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, null);

        SearchView searchView = view.findViewById(R.id.fragment_main_search_view);
        searchView.clearFocus();

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)mContext).setSupportActionBar(toolbar);
//        SearchView searchViewQuery = view.findViewById(R.id.searchViewQuery);
//        ImageView imageViewSearchMenu = view.findViewById(R.id.imageViewSearchMenu);
//        CoordinatorLayout cordinatorLayoutActivity = view.findViewById(R.id.activity_main_coordinator_layout);
//        NestedScrollView nestedScrollView=view.findViewById(R.id.nestedScrollView);
//
//
//        EditText searchEditText = searchViewQuery.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null));
//        searchEditText.setHintTextColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryDark,null));
//
//        ImageView searchImage = searchViewQuery.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
//        searchImage.setVisibility(View.GONE);
//        ViewGroup linearLayoutSearchView =(ViewGroup) searchImage.getParent();
//        linearLayoutSearchView.removeView(searchImage);
//        linearLayoutSearchView.addView(searchImage);
//        searchImage.setAdjustViewBounds(true);
//        searchImage.setMaxWidth(0);
//        searchImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        searchImage.setImageDrawable(null);
//
//        RecyclerView recycleViewNews = view.findViewById(R.id.recycleView);
//        recycleViewNews.setHasFixedSize(true);
//        recycleViewNews.setLayoutManager(new LinearLayoutManager(mContext));
//        SearchAdapter mAdapter = new SearchAdapter();
//        recycleViewNews.setAdapter(mAdapter);
//        recycleViewNews.setNestedScrollingEnabled(false);

        return view;
    }
}
