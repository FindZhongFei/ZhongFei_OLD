package com.fzhongfei.findzhongfei_final.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class InternetAvailability {

    // CHECK INTERNET AVAILABILITY
    public static boolean internetIsAvailable(final Context mContext) {
        android.net.ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetworkInfo = null;
        if(cm != null) {
            activeNetworkInfo = cm.getActiveNetworkInfo();
        }

        boolean isConnected = activeNetworkInfo != null &&
                activeNetworkInfo.isConnected();

        if(isConnected) {
            return true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setIcon(R.drawable.internet_logo);
            builder.setTitle("NO INTERNET CONNECTION");
            builder.setMessage("Please Check Your Internet or Wifi Connection.");
            builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });
            builder.show();
            return false;
        }
    }

}



//    public static NetworkInfo getNetworkInfo(Context context){
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        android.net.NetworkInfo activeNetworkInfo = null;
//        if(cm != null) {
//            activeNetworkInfo = cm.getActiveNetworkInfo();
//        }
//
//        return activeNetworkInfo;
//    }
//
//    public static boolean isConnected(Context context){
//        NetworkInfo info = InternetAvailability.getNetworkInfo(context);
//        return (info != null && info.isConnected());
//    }
//
//    public static boolean isConnectedWifi(Context context){
//        NetworkInfo info = InternetAvailability.getNetworkInfo(context);
//        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
//    }
//
//    public static boolean isConnectedMobile(Context context){
//        NetworkInfo info = InternetAvailability.getNetworkInfo(context);
//        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
//    }
//
//    public static boolean isConnectedFast(Context context){
//        NetworkInfo info = InternetAvailability.getNetworkInfo(context);
//        return (info != null && info.isConnected() && InternetAvailability.isConnectionFast(info.getType(),info.getSubtype()));
//    }
//
//    public static boolean isConnectionFast(int type, int subType){
//        if(type==ConnectivityManager.TYPE_WIFI){
//            return true;
//        }else if(type==ConnectivityManager.TYPE_MOBILE){
//            switch(subType){
//                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    return false; // ~ 14-64 kbps
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    return true; // ~ 400-1000 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    return true; // ~ 600-1400 kbps
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    return false; // ~ 100 kbps
//                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    return true; // ~ 2-14 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    return true; // ~ 700-1700 kbps
//                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    return true; // ~ 1-23 Mbps
//                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    return true; // ~ 400-7000 kbps
//                /*
//                 * Above API level 7, make sure to set android:targetSdkVersion
//                 * to appropriate level to use these
//                 */
//                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//                    return true; // ~ 1-2 Mbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//                    return true; // ~ 5 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//                    return true; // ~ 10-20 Mbps
//                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//                    return false; // ~25 kbps
//                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//                    return true; // ~ 10+ Mbps
//                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                default:
//                    return false;
//            }
//        }else{
//            return false;
//        }
//    }
