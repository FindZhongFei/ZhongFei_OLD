package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.CompanyAdapter;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainFragment extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "MainFragment";
    Context mContext;

    // VIEWS
    View view;

    // VARIABLES
//    private Companies mCompanies[]; //card - card_data
//    private int i;
//    ListView mListView; //listView
//    public static HashMap<String, String> hashCompData = new HashMap<>();
    private CompanyAdapter mCompanyAdapter; //arrayAdapter
    public static ArrayList<JSONObject> hashMapArrayList = new ArrayList<>();
    List<Companies> mCompaniesList; //List<cards> rowItems

    SharedPreferences companySharedPreferences;
    SharedPreferences userSharedPreferences;

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

        companySharedPreferences = this.getActivity().getSharedPreferences("companyPreference", 0);
        userSharedPreferences = this.getActivity().getSharedPreferences("userPreference", 0);

        mCompaniesList = new ArrayList<>();

        ArrayList<Companies> companiesArrayList = new ArrayList<>();

        Companies company;
        JSONObject jsonObject;

        for(int i = 0; i < hashMapArrayList.size(); i++)
        {
            jsonObject = hashMapArrayList.get(i);

//            company.setCompId(mContext, jsonObject.optString("comp_id"));
//            company.setCompName(mContext, jsonObject.optString("comp_name"));
//            company.setImageLogo(mContext, jsonObject.optString("comp_logo"));
//            company.setCompType(mContext, jsonObject.optString("comp_type"));
//            company.setCompSubType(mContext, jsonObject.optString("comp_subtype"));

            company = new Companies(jsonObject.optInt("comp_id"),
                    jsonObject.optString("comp_logo"),
                    jsonObject.optString("comp_id"),
                    jsonObject.optString("comp_token"),
                    jsonObject.optString("comp_name"),
                    jsonObject.optString("comp_type"),
                    jsonObject.optString("comp_subtype"),
                    jsonObject.optString("logo_val"));
            companiesArrayList.add(i, company);
        }

        // REMOVE ANY DUPLICATE COMPANIES FROM LIST - 'LinkedHashSet' PRESERVES INSERTION ORDER AS WELL
        Set<Companies> nonDuplicatedCompanies = new LinkedHashSet<>(companiesArrayList);
        companiesArrayList.clear();
        companiesArrayList.addAll(nonDuplicatedCompanies);
        mCompaniesList.addAll(companiesArrayList);

        mCompanyAdapter = new CompanyAdapter(mContext, R.layout.layout_item, mCompaniesList);
        mCompanyAdapter.notifyDataSetChanged();

        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);

        flingContainer.setAdapter(mCompanyAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                mCompaniesList.remove(0);
                mCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(mContext, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(mContext, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                Companies company = new Companies(1, "",
//                        "", "Last Company", "Last Type",
//                        "Last SubType");
//                mCompaniesList.add(company);
//                mCompanyAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(mContext, "Clicked!");
            }
        });

        loadCompanies();

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

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
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
}
