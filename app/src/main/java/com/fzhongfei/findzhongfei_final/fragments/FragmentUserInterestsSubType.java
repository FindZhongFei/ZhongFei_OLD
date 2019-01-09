package com.fzhongfei.findzhongfei_final.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.SubInterestsAdapter;
import com.fzhongfei.findzhongfei_final.model.SubInterestItem;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentUserInterestsSubType extends DialogFragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = FragmentUserInterestsSubType.class.getSimpleName();
    Context mContext;

    // VIEWS
    View view;
    List<SubInterestItem> mSubInterestsList;

    // VARIABLES
    ArrayList<String> interests = new ArrayList<>();
    public static ArrayList<String> subInterests = new ArrayList<>();

    UserProfile mUserProfile;

    public FragmentUserInterestsSubType() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        interests = args.getStringArrayList("user_interests");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        view = inflater.inflate(R.layout.fragment_user_sub_interests, null);

        Log.d(TAG, "onCreate: Running...");

        mContext = getActivity();

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        Button submitButton;

        // INITIALIZING VIEWS
        mSubInterestsList = new ArrayList<>();

        submitButton = view.findViewById(R.id.user_sub_interests_submit_button);

        // ACTIVITY STUFF
        if (interests != null) {
            for (int i = 0; i < interests.size(); i++) {
                for (int j = 0; j < interestsHas(interests.get(i)).length; j++) {
                    mSubInterestsList.add(new SubInterestItem(interestsHas(interests.get(i))[j].toString(), R.drawable.img_wall_1));
                }
            }
        }
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view_sub_interests);
        SubInterestsAdapter mAdapter = new SubInterestsAdapter(mContext, mSubInterestsList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(mAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserInterests();
            }
        });

        return view;
    }

    private String[] interestsHas(String subInterest) {
        switch (subInterest) {
            case "Aerospace industry":
                return new String[]{"Aerospace industry"};

            case "Agriculture":
                return new String[]{"Fishing industry", "Timber industry", "Tobacco industry"};

            case "Chemical industry":
                return new String[]{"Pharmaceutical industry"};

            case "Computer industry":
                return new String[]{"Software industry"};

            case "Construction industry":
                return new String[]{"Construction industry"};

            case "Defense industry":
                return new String[]{"Arms industry"};

            case "Education industry":
                return new String[]{"Education industry"};

            case "Energy industry":
                return new String[]{"Petroleum industry", "Electrical power industry"};

            case "Entertainment industry":
                return new String[]{"Entertainment industry"};

            case "Financial services industry":
                return new String[]{"Insurance industry"};

            case "Food production":
                return new String[]{"Fruit industry"};

            case "Health care industry":
                return new String[]{"Health care industry"};

            case "Hospitality industry":
                return new String[]{"Hospitality industry"};

            case "Information industry":
                return new String[]{"Information industry"};

            case "Manufacturing":
                return new String[]{"Automotive industry", "Electronics industry", "Pulp and paper industry", "Steel industry", "Shipbuilding industry"};

            case "Mass media":
                return new String[]{"Broadcasting", "Film industry", "Music industry", "News media", "Publishing", "World Wide Web"};

            case "Mining":
                return new String[]{"Mining"};

            case "Telecommunications industry":
                return new String[]{"Internet"};

            case "Transport industry":
                return new String[]{"Transport industry"};

            case "Water industry":
                return new String[]{"Water industry"};

            case "Direct Selling industry":
                return new String[]{"Direct Selling industry"};

            default:
                return new String[]{"Other"};
        }
    }

    private String belongsTo(String subInterest) {
        switch (subInterest) {
            case "Aerospace industry":
                return "Aerospace industry | ";

            case "Fishing industry":
            case "Timber industry":
            case "Tobacco industry":
                return "Agriculture | ";

            case "Pharmaceutical industry":
                return "Chemical industry";

            case "Software industry":
                return "Computer industry | ";

            case "Construction industry":
                return "Construction industry | ";

            case "Arms industry":
                return "Defense industry | ";

            case "Education industry":
                return "Education industry | ";

            case "Electrical power industry":
            case "Petroleum industry":
                return "Energy industry | ";

            case "Entertainment industry":
                return "Entertainment industry | ";

            case "Insurance industry":
                return "Financial services industry | ";

            case "Fruit production":
                return "Food industry | ";

            case "Health care industry":
                return "Health care industry | ";

            case "Hospitality industry":
                return "Hospitality industry | ";

            case "Information industry":
                return "Information industry | ";

            case "Automotive industry":
            case "Electronics industry":
            case "Pulp and paper industry":
            case "Steel industry":
            case "Shipbuilding industry":
                return "Manufacturing | ";

            case "Broadcasting":
            case "Film industry":
            case "Music industry":
            case "News media":
            case "Publishing":
            case "World Wide Web":
                return "Mass media | ";

            case "Mining":
                return "Mining | ";

            case "Internet":
                return "Telecommunications industry | ";

            case "Transport industry":
                return "Transport industry | ";

            case "Water industry":
                return "Water industry | ";

            case "Direct Selling industry":
                return "Direct Selling industry | ";

            default:
                return "Other | ";
        }
    }

    // SUBMIT SUB INTERESTS
    private void submitUserInterests() {
        JSONObject subInterestsJsonObject = new JSONObject();

        customStringRequest registerRequest = new customStringRequest("user/interests.php");

        HashMap<String, String> Params = new HashMap<>();

        Params.put("action", "user_interests");
        Params.put("user_token", mUserProfile.getUserToken());

        ArrayList<String> belongsToInterest = new ArrayList<>();
        for (int i = 0; i < subInterests.size(); i++) {
            String interest = belongsTo(subInterests.get(i));

            try {
                if (!belongsToInterest.contains(interest)) {
                    Log.d(TAG, "submitUserInterests: NEW INTEREST: " + interest);
                    Log.d(TAG, "submitUserInterests: ADD SUBINTEREST: " + subInterests.get(i));
                    subInterestsJsonObject.put(interest, subInterests.get(i));
                    belongsToInterest.add(interest);
                } else {
                    String existingInterest = subInterestsJsonObject.get(interest).toString();
                    Log.d(TAG, "submitUserInterests: EXISTING INTEREST: " + interest + " containing: " + existingInterest);
                    Log.d(TAG, "submitUserInterests: ADD SUBINTEREST: " + subInterests.get(i));
                    subInterestsJsonObject.put(interest, existingInterest + " | " + subInterests.get(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "submitUserInterests: subInterest:: " + subInterestsJsonObject);
        Log.d(TAG, "submitUserInterests: belongToInterest:: " + belongsToInterest);

        int interestCounter = 0;
        int sizeOfInterest = belongsToInterest.size();
        String interestKey = "";
        String interestValue = "";
        String tempValue = "";

        while (interestCounter < sizeOfInterest) {
            interestKey = belongsToInterest.get(interestCounter);
            try {
                if (interestValue.isEmpty()) {
                    interestValue = interestKey + subInterestsJsonObject.getString(interestKey);
                    Log.d(TAG, "submitUserInterests: Initial value: " + interestKey + " to GET: " + interestValue);
                } else {
                    tempValue = interestValue;
                    interestValue = tempValue + ", " + interestKey + subInterestsJsonObject.getString(interestKey);
                    Log.d(TAG, "submitUserInterests: after init value: " + interestKey + " to GET: " + interestValue);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //TO DO: catching the try and catch in well format for the user to get relevant error
                //TO DO: logging errors
            }
            interestCounter++;
        }

        Params.put("user_interests", interestValue);
        Log.d(TAG, "submitUserInterests: THE INTEREST IS: " + interestValue);
        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_interests");
        registerRequest.startConnection(mContext, callBack, Params);
    }
}
