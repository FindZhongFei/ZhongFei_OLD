package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.HistoryItem;

import java.util.List;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder> {

    private Context mContext;
    private List<HistoryItem> mHistoryItemList;

    public HistoryRVAdapter(Context context, List<HistoryItem> historyItems) {
        mContext = context;
        mHistoryItemList = historyItems;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_history_cardview, null);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem historyitem = mHistoryItemList.get(position);

        holder.companyThumbnail.setImageDrawable(mContext.getResources().getDrawable(historyitem.getCompanyThumbnail()));
        holder.txtCompName.setText(historyitem.getCompanyName());
        holder.txtCompType.setText(historyitem.getCompanyType());
    }

    @Override
    public int getItemCount() {
        return mHistoryItemList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView companyThumbnail;
        TextView txtCompName, txtCompType;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            companyThumbnail = itemView.findViewById(R.id.history_company_thumbnail);
            txtCompName = itemView.findViewById(R.id.history_company_name);
            txtCompType = itemView.findViewById(R.id.history_company_type);
        }
    }
}
