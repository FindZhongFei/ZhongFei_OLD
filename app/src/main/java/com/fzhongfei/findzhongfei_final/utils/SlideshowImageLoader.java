package com.fzhongfei.findzhongfei_final.utils;

import android.graphics.Bitmap;

public class SlideshowImageLoader {

    private Bitmap sliderImageBitmap;
    private String sliderImageUrl;

    public void setSliderImageUrl(String sliderImageUrl) {
        this.sliderImageUrl = sliderImageUrl;
    }
    public String getSliderImageUrl() {
        return sliderImageUrl;
    }

    public void setSliderImageBitmap(Bitmap imageBitmap) {
        this.sliderImageBitmap = imageBitmap;
    }
    public Bitmap getSliderImageBitmap() {
        return sliderImageBitmap;
    }
}
