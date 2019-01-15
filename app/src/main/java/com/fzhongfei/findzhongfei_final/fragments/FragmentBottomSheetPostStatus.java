package com.fzhongfei.findzhongfei_final.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;

public class FragmentBottomSheetPostStatus extends DialogFragment {

    Context mContext;

    // VIEWS
    View view;

    public FragmentBottomSheetPostStatus() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_status, container, false);

        mContext = getActivity();

        ImageButton close = view.findViewById(R.id.full_screen_dialog_button_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).onBackPressed();
            }
        });

        return view;
    }

}
