package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.UserInterestsSubTypeActivity;
import com.fzhongfei.findzhongfei_final.model.SubInterestItem;

import java.util.List;

public class SubInterestsAdapter extends RecyclerView.Adapter<SubInterestsAdapter.SubInterestViewHolder> {

    private Context mContext;
    private List<SubInterestItem> mSubInterestItems;

    public SubInterestsAdapter(Context context, List<SubInterestItem> subInterestItems) {
        mContext = context;
        mSubInterestItems = subInterestItems;
    }

    @NonNull
    @Override
    public SubInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.layout_interests_sub_type_cards, parent, false);

        return new SubInterestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubInterestViewHolder holder, int position) {
        final SubInterestItem subInterestItem = mSubInterestItems.get(position);

        holder.txtTitle.setText(mSubInterestItems.get(position).getTitle());
        holder.thumbnail.setImageResource(mSubInterestItems.get(position).getThumbnail());

        final SubInterestViewHolder Holder = holder;

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable clickedImage = mContext.getResources().getDrawable(R.drawable.background_highlight_image);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(Holder.thumbnail.getForeground() == null)
                    {
                        Toast.makeText(mContext,"Clicked: " + subInterestItem.getTitle() + "...." + UserInterestsSubTypeActivity.subInterests.size(), Toast.LENGTH_SHORT).show();
                        Holder.thumbnail.setForeground(new ColorDrawable(mContext.getColor(R.color.colorGreen)));
                        Holder.thumbnail.setForeground(clickedImage);

                        UserInterestsSubTypeActivity.subInterests.add(subInterestItem.getTitle());
                    }
                    else if(Holder.thumbnail.getForeground() != null)
                    {
                        Toast.makeText(mContext,"Removed: " + subInterestItem.getTitle() + "...." + UserInterestsSubTypeActivity.subInterests.size(), Toast.LENGTH_SHORT).show();
                        UserInterestsSubTypeActivity.subInterests.remove(subInterestItem.getTitle());
                        Holder.thumbnail.setForeground(null);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubInterestItems.size();
    }

    class SubInterestViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView txtTitle;

        public SubInterestViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.sub_interests_thumbnail);
            txtTitle = itemView.findViewById(R.id.sub_interests_title);
        }
    }
}
