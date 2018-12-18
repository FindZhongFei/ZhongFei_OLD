package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.content.SharedPreferences;

public class trackRequestPosition {

    private SharedPreferences sharedPreference;
    public int startPosition;

    public trackRequestPosition(Context context){
        startPosition = 0;
        resetTrackPosition(context);
    };
    public void resetTrackPosition(Context context)
    {
        sharedPreference = context.getSharedPreferences("requestPosition",0);
    }
    private void setPosition(int pos)
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt("requestPosition", pos);
        editor.apply();
        editor.commit();
    }

    public void IncrementPosition()
    {
        int currentPosition = this.getCurrentPosition();
        setPosition(currentPosition + 5);
    }
    public int getCurrentPosition()
    {
        int currentPosition = sharedPreference.getInt("requestPosition", 0);
        return currentPosition;
    }

}
