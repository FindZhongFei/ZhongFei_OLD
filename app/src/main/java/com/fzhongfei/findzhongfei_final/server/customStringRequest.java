package com.fzhongfei.findzhongfei_final.server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity1;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity2;
import com.fzhongfei.findzhongfei_final.activity.CompanyRegistrationActivity3;
import com.fzhongfei.findzhongfei_final.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customStringRequest {
        private String  urlPath = Constants.SERVER_URL;
        private HashMap params = new HashMap();
        private JSONObject reqResponse = new JSONObject();
        private String errorMessage = null;
        private static final String TAG = "customStringRequest";
        private boolean responseIsReturned = false;

        public customStringRequest(){}

        public customStringRequest(String domain) {
            this.setUrlPath(domain);
        }

        public void setUrlPath(String domain) {
            this.urlPath = this.urlPath + domain;
        }

        public void setParams(HashMap params) {
            this.params = params;
        }

        public void startConnection(final Context context, final serverCallBack callBack, HashMap<String,String > params) {
            StringRequest requestHandler = new StringRequest(Request.Method.POST, this.urlPath ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: response has been returned");
                            Log.d(TAG, "onResponse: my response is " + response);
                            try {
                                JSONObject responseArray = new JSONObject(response);
                                Log.d(TAG, "onResponse: JSON ARRAY  " + responseArray.toString());

//                                Toast.makeText(context, "response" + responseArray.toString(), Toast.LENGTH_LONG).show();

                                customStringRequest.this.reqResponse = responseArray;
                            } catch (JSONException e) {
                                customStringRequest.this.errorMessage = e.getMessage();
                                Log.d(TAG, "onResponse: Error for json from string response ::: " + e.getMessage());
                            }
                            customStringRequest.this.responseIsReturned = true;
                            callBack.onSuccess(customStringRequest.this.reqResponse);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(context != null)
                    {
                        if(context.toString().contains("CompanyRegistrationActivity1")) {
                            ((CompanyRegistrationActivity1) context).stopCompanyRegistrationConnection1();
                        } else if(context.toString().contains("CompanyRegistrationActivity2")) {
                            ((CompanyRegistrationActivity2) context).stopCompanyRegistrationConnection2();
                        } else if(context.toString().contains("CompanyRegistrationActivity3")) {
                            ((CompanyRegistrationActivity3) context).stopCompanyRegistrationConnection3();
                        }

                        Toast.makeText(context,"Server error...contact support", Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "onErrorResponse: " + error.toString());

                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> gotParams = callBack.getParams();
                    Log.d(TAG, "getParams: " + gotParams);
                    Map<String, String>  params = new HashMap<String ,String>();
                    params = gotParams;
                    return params;
                }};
            Log.d(TAG, "startConnection: response delivered: " + requestHandler.hasHadResponseDelivered());

            Mysingleton.getInstance(context).addTorequestque(requestHandler);
        }

        public boolean responseIsBack() {
            return this.responseIsReturned;
        }
        public JSONObject getResponse() {
            Log.d(TAG, "getResponse: " + this.reqResponse);
            return this.reqResponse;
        }
}
