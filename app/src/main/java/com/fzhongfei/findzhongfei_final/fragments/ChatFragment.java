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
import com.fzhongfei.findzhongfei_final.adapter.ChatRoomsAdapter;
import com.fzhongfei.findzhongfei_final.adapter.FavoriteCompanyAdapter;
import com.fzhongfei.findzhongfei_final.helper.SimpleDividerItemDecoration;
import com.fzhongfei.findzhongfei_final.model.ChatRoom;
import com.fzhongfei.findzhongfei_final.model.FavoriteCompany;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "ChatFragment";
    Context mContext;

    // VIEWS
    View view;

    // VARIABLES
    private ArrayList<ChatRoom> chatRoomArrayList;
    private ChatRoomsAdapter mAdapter;
    private RecyclerView recyclerView;

    public ChatFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Running...");

        view = inflater.inflate(R.layout.fragment_chat, null);

        mContext = getActivity();

        // INITIALIZING VIEWS
        recyclerView = view.findViewById(R.id.chat_recycler_view);
        chatRoomArrayList = new ArrayList<>();
        mAdapter = new ChatRoomsAdapter(mContext, chatRoomArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity().getApplicationContext()
        ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        chatRoomArrayList.add(new ChatRoom("ID", "Neud", "YOLO ma G!!!", "5:00 PM", 0));
        chatRoomArrayList.add(new ChatRoom("ID", "Yene", "Where are you?", "1:52 AM", 1));
        chatRoomArrayList.add(new ChatRoom("ID", "Crazy Ass Nigga", "I was like..No ways", "2:32 PM", 5));
        chatRoomArrayList.add(new ChatRoom("ID", "MC", "IKR", "12:05 PM", 0));

        return view;
    }
}
