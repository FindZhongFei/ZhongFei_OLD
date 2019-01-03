package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.ChatComposeActivity;
import com.fzhongfei.findzhongfei_final.model.ChatList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private static Context mContext;
    private ArrayList<ChatList> chatRoomArrayList;
    private static String today, thisMonth, thisYear;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, message, timestamp, count;
        private RelativeLayout chatRelativeLayout;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            message = view.findViewById(R.id.message);
            timestamp = view.findViewById(R.id.timestamp);
            count = view.findViewById(R.id.count);
            chatRelativeLayout = view.findViewById(R.id.chat_relative_layout);
        }
    }

    public ChatListAdapter(Context mContext, ArrayList<ChatList> chatRoomArrayList) {
        this.mContext = mContext;
        this.chatRoomArrayList = chatRoomArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_rooms_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChatList chatRoom = chatRoomArrayList.get(position);
        holder.name.setText(chatRoom.getSenderName());
        holder.message.setText(chatRoom.getLastMessage());

        if(chatRoom.getUnreadCount() > 0)
        {
            holder.count.setText(String.valueOf(chatRoom.getUnreadCount()));
            holder.count.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.count.setVisibility(View.GONE);
        }

        holder.timestamp.setText(getTimeStamp(chatRoom.getMessageTime()));

        holder.chatRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext.getApplicationContext(),
                        ChatComposeActivity.class).putExtra("receiverName", holder.name.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRoomArrayList.size();
    }

    private static String getTimeStamp(String dateStr) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestamp = "";
        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        thisMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        thisYear = String.valueOf(calendar.get(Calendar.YEAR));

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String dateToday = todayFormat.format(date);
            String monthOfToday = monthFormat.format(date);
            String yearOfToday = yearFormat.format(date);
//            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            if(dateToday.equals(today))
            {
                format = new SimpleDateFormat("hh:mm a");
            }
            else if(Integer.valueOf(dateToday).equals(Integer.valueOf(today) - 1) && monthOfToday.equals(thisMonth) && yearOfToday.equals(thisYear))
            {
                timestamp = "yesterday";
            }
            else if(monthOfToday.equals(thisMonth) && yearOfToday.equals(thisYear))
            {
                format = new SimpleDateFormat("MM/dd/yy");
            }
            else if(!monthOfToday.equals(thisMonth) && yearOfToday.equals(thisYear))
            {
                format = new SimpleDateFormat("LLL dd");
            }
            else
            {
                format = new SimpleDateFormat("LLL dd, yyyy");
            }

            if(timestamp.equals(""))
            {
                timestamp = format.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

//    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//
//        private GestureDetector gestureDetector;
//        private ChatListAdapter.ClickListener clickListener;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ChatListAdapter.ClickListener clickListener) {
//            this.clickListener = clickListener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//                clickListener.onClick(child, rv.getChildPosition(child));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }
}
