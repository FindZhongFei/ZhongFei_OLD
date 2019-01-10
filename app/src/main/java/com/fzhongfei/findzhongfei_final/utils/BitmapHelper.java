package com.fzhongfei.findzhongfei_final.utils;

import android.graphics.Bitmap;

public class BitmapHelper {

    private Bitmap bitmap = null;
    private static final BitmapHelper instance = new BitmapHelper();

    public BitmapHelper() {

    }

    public static BitmapHelper getInstance() {
        return instance;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap()
    {
        return bitmap;
    }
}
