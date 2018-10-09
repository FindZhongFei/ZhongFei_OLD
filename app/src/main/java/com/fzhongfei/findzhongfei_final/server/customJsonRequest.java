package com.fzhongfei.findzhongfei_final.server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.fzhongfei.findzhongfei_final.constants.Constants;

import org.json.JSONObject;

import java.util.HashMap;

public class customJsonRequest implements Response.ProgressListener {
    private String  register_domain = Constants.SERVER_URL + "register/index.php";
    private String  login_domain = Constants.SERVER_URL + "login/index.php";
    private HashMap params = new HashMap();
    private JSONObject reqResponse = new JSONObject();
    private boolean errorStatus = false;
    private String errorMessage = null;
    private static final String TAG = "customJsonRequest";
    private boolean responseIsReturned = false;


    public customJsonRequest(){}

    public customJsonRequest(String domain) {
        this.setDomainName(domain);
    }

    public void setDomainName(String domain) {
        this.register_domain = domain;
    }

    public void setParams(HashMap params) {
        this.params = params;
    }

    public void startConnection(final Context context, final serverCallBack callBack) {
        JSONObject localResponse;
        JsonObjectRequest requestHandler = null;

        requestHandler = new JsonObjectRequest(Request.Method.POST, this.register_domain,
                new JSONObject(this.params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: response has been returned");
                        Log.d(TAG, "onResponse: my respone is " + response.toString());
                        customJsonRequest.this.reqResponse = response;
                        customJsonRequest.this.responseIsReturned = true;
                        callBack.onSuccess(response);
                    }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
                Toast.makeText(context,"Some error found ...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        Log.d(TAG, "startConnection: response delivered: " + requestHandler.hasHadResponseDelivered());

        Mysingleton.getInstance(context).addTorequestque(requestHandler);
    }

    public boolean responseIsBack() {
        return this.responseIsReturned;
    }
    public JSONObject getResponse() {
        Log.d(TAG, "getResponse: CALLEEEEEEEED" + this.reqResponse);
        return this.reqResponse;
    }

    @Override
    public void onProgress(long transferredBytes, long totalSize) {

    }
}
