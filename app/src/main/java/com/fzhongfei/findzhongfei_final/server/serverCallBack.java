package com.fzhongfei.findzhongfei_final.server;

import org.json.JSONObject;

import java.util.HashMap;

public interface serverCallBack {
    void onSuccess(JSONObject result);
    boolean isSuccess();
    String getSuccessMessage();
    String getErrorMessage();
    HashMap<String, String> getParams();
    void setParams(HashMap<String,String> params);
}
