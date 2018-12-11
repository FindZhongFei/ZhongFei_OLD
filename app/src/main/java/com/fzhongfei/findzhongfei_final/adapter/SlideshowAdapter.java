package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.utils.SlideshowImageLoader;

import java.util.List;

public class SlideshowAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    public static List<SlideshowImageLoader> mImageLoader;
    public int[] slideshowImages = {R.drawable.image_placeholder,
                                    R.drawable.profile_picture,
                                    R.drawable.img_wall_9,
                                    R.drawable.img_wall_1,
                                    R.drawable.img_wall_2,
                                    R.drawable.img_wall_3,
                                    R.drawable.img_wall_4,
                                    R.drawable.img_wall_5,
                                    R.drawable.img_wall_9,
                                    R.drawable.img_wall_10,
                                    R.drawable.img_wall_11,
                                    R.drawable.img_wall_12,
                                    R.drawable.img_wall_13,
                                    R.drawable.img_wall_14};

    public SlideshowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return slideshowImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.layout_main_companies_slide_show, container, false);

        ImageView imageView = view.findViewById(R.id.slide_show_image);
        imageView.setImageResource(slideshowImages[position]);

        if(mImageLoader != null)
        {
            imageView.setImageBitmap(mImageLoader.get(position).getSliderImageBitmap());
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout) object);
    }
}
