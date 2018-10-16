package com.fzhongfei.findzhongfei_final.server;

import org.json.JSONObject;

import java.util.HashMap;

public interface serverCallBack {
    boolean isSuccess();
    String getSuccessMessage();
    String getErrorMessage();
    HashMap<String, String> getParams();
    void onSuccess(JSONObject result);
    void setParams(HashMap<String,String> params);
    void SetRequestType(String type);
    String GetRequestType();
}
