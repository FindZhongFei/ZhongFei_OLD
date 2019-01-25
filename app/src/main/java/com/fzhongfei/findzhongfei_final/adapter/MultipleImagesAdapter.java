package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fzhongfei.findzhongfei_final.R;

import java.util.List;

public class MultipleImagesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bitmap> mArrayBitmap;

    public MultipleImagesAdapter(Context context, List<Bitmap> mArrayUri)
    {
        this.mContext = context;
        this.mArrayBitmap = mArrayUri;
    }

    @Override
    public int getCount() {
        return mArrayBitmap.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayBitmap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.layout_grid_images, parent, false);

        ImageView ivGallery = itemView.findViewById(R.id.ivGallery);
        ivGallery.setImageBitmap(mArrayBitmap.get(position));

        return itemView;
    }
}
