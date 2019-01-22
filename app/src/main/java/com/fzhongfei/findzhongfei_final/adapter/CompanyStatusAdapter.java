package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.StatusModel;

import java.util.ArrayList;

public class CompanyStatusAdapter extends RecyclerView.Adapter<CompanyStatusAdapter.MyStatusViewHolder> {

    Context mContext;
    ArrayList<StatusModel> mStatusModelArrayList = new ArrayList<>();

    public CompanyStatusAdapter(Context context, ArrayList<StatusModel> statusModelArrayList) {
        mContext = context;
        mStatusModelArrayList = statusModelArrayList;
    }

    @NonNull
    @Override
    public MyStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_status_row, parent, false);
        MyStatusViewHolder myStatusViewHolder = new MyStatusViewHolder(view);

        return myStatusViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyStatusViewHolder holder, int position) {
        final StatusModel statusModel = mStatusModelArrayList.get(position);

        holder.companyName.setText(statusModel.getCompanyName());
        holder.statusTime.setText(statusModel.getStatusTime());
        holder.statusContent.setText(statusModel.getStatusContent());
        holder.likeButton.setText(statusModel.getLikes());
        holder.commentButton.setText(statusModel.getComments());
        holder.likeButton.setText(statusModel.getLikes());

//        holder.companyThumbnail.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return mStatusModelArrayList.size();
    }

    public class MyStatusViewHolder extends RecyclerView.ViewHolder {

        TextView companyName, statusTime, statusContent;
        GridView photoGrid;
        ImageView companyThumbnail;
        Button likeButton, commentButton, followButton;

        public MyStatusViewHolder(View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.status_company_name);
            statusTime = itemView.findViewById(R.id.status_time);
            statusContent = itemView.findViewById(R.id.status_content);
            photoGrid = itemView.findViewById(R.id.status_grid_view);
            companyThumbnail = itemView.findViewById(R.id.status_profile_picture);
            likeButton = itemView.findViewById(R.id.status_btn_like);
            commentButton = itemView.findViewById(R.id.status_btn_comment);
            followButton = itemView.findViewById(R.id.status_btn_follow_company);
        }
    }

    final static int TARGET_IMAGE_VIEW_WIDTH = 200;
    final static int TARGET_IMAGE_VIEW_HEIGHT = 200;

    private int calculateInSampleSize(BitmapFactory.Options bitmapOptions)
    {
        final int PHOTO_WIDTH = bitmapOptions.outWidth;
        final int PHOTO_HEIGHT = bitmapOptions.outHeight;

        // SCALE OF RESIZED BITMAP AND IMAGE VIEW
        int scale = 1;

        if(PHOTO_WIDTH > TARGET_IMAGE_VIEW_WIDTH || PHOTO_HEIGHT > TARGET_IMAGE_VIEW_HEIGHT)
        {
            final int halfPhotoWidth = PHOTO_WIDTH / 2;
            final int halfPhotoHeight = PHOTO_HEIGHT / 2;

            while(  halfPhotoWidth / scale > TARGET_IMAGE_VIEW_WIDTH ||
                    halfPhotoHeight / scale > TARGET_IMAGE_VIEW_HEIGHT)
            {
                scale *= 2;
            }
        }

        return scale;
    }

    private Bitmap decodeBitmap(String imageFile)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // DUMMY BITMAP
        options.inJustDecodeBounds = true;
        byte[] decodedLogo = Base64.decode(imageFile, Base64.DEFAULT);
        BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, options);

        options.inSampleSize = calculateInSampleSize(options);
        // LOADING THE ACTUAL BITMAP
        options.inJustDecodeBounds = true;

        return BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, options);
    }
}
