package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.ClickedCompany;
import com.fzhongfei.findzhongfei_final.constants.Constants;
import com.fzhongfei.findzhongfei_final.fragments.MainFragment1;
import com.fzhongfei.findzhongfei_final.helper.TaskBitmapFromURL;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.server.Mysingleton;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.BitmapHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.fzhongfei.findzhongfei_final.server.callBackImplement.TAG;

public class MainFragmentCompaniesRecyclerViewAdapter extends RecyclerView.Adapter<MainFragmentCompaniesRecyclerViewAdapter.MyCompanyViewHolder> {

    private Context mContext;
    private List<Companies> mCompaniesList;
    private Bitmap placeHolderBitmap;
    private String companyImageFile;

    private class AsyncDrawable extends BitmapDrawable {
        // REFERENCE TO 'TaskBitmapFromURL' CLASS
        final private WeakReference<TaskBitmapFromURL> taskReference;

        private AsyncDrawable(Resources resources,
                             Bitmap bitmap,
                             TaskBitmapFromURL taskBitmapFromURL)
        {
            super(resources, bitmap);
            taskReference = new WeakReference<>(taskBitmapFromURL);
        }

        private TaskBitmapFromURL getTask()
        {
            return taskReference.get();
        }
    }

    public MainFragmentCompaniesRecyclerViewAdapter(Context context, List<Companies> companiesList) {
        mContext = context;
        mCompaniesList = companiesList;
    }

    @NonNull
    @Override
    public MyCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewGroup nullParent = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_fragment_cardview_companies, nullParent);

        return new MyCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCompanyViewHolder holder, final int position) {
        Companies company = mCompaniesList.get(position);
        final TaskBitmapFromURL bitmapWorker;
        final String companyName = company.getCompName();
        final String companyToken = company.getCompToken();
        final String companyType = company.getCompType();
        final String companyImageUrl = company.getImageUrl();
        Drawable imageDrawable = mContext.getResources().getDrawable(R.drawable.loading_image_background);
        Drawable textDrawable = mContext.getResources().getDrawable(R.drawable.loading_text_background);
        LinearLayout.LayoutParams companyTextParams;
        RelativeLayout.LayoutParams imageParams;

        if(companyName == null && companyType == null && companyImageUrl == null)
        {
            companyTextParams = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            );

            imageParams = new RelativeLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            );

            companyTextParams.setMargins(0, 0, 0, 15);
            imageParams.setMargins(0, 0, 0, 15);

            holder.companyThumbnail.setImageDrawable(imageDrawable);
            holder.companyThumbnail.setLayoutParams(imageParams);

            holder.companyName.setText("");
            holder.companyName.setBackground(textDrawable);
            holder.companyName.setLayoutParams(companyTextParams);
            holder.companyName.setWidth(570);

            holder.companyType.setText("");
            holder.companyType.setBackground(textDrawable);
            holder.companyType.setLayoutParams(companyTextParams);
            holder.companyType.setWidth(400);
        }
        else
        {
            holder.companyName.setText(companyName);
            holder.companyType.setText(companyType);

//            customStringRequest imageRequest = new customStringRequest();
//            HashMap<String, String> params = new HashMap<>();
//
//            params.put("requestType", "requestCompLogo");
//            params.put("logoUrl", companyImageUrl);
//            params.put("compToken", company.getCompToken());
//
//            imageRequest.setUrlPath("comp/fetchImage.php");
//            imageRequest.setParams(params);
//
//            callBackImplement callBack = new callBackImplement(mContext);
//            callBack.setParams(params);
//            callBack.SetRequestType("requestLoadedCompaniesImages");
//
//            imageRequest.startConnection(mContext, callBack, params);

            bitmapWorker = new TaskBitmapFromURL(mContext, holder.companyThumbnail, companyToken, 200, 500);
            final Bitmap bitmap = MainFragment1.getBitmapFormMemoryCache(companyImageUrl);

            RequestQueue queue = Volley.newRequestQueue(mContext);
            final String requestUrl = Constants.SERVER_URL + "comp/fetchImage.php";
            final HashMap<String, String> params = new HashMap<>();

            params.put("requestType", "requestCompLogo");
            params.put("logoUrl", companyImageUrl);
            params.put("compToken", companyToken);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseArray = new JSONObject(response);

                                companyImageFile = responseArray.get("imageFile").toString();

                                if(bitmap != null)
                                {
                                    // THE IMAGE IS IN CACHE LET'S ASSIGN IT STRAIGHT TO THE IMAGE VIEW
                                    holder.companyThumbnail.setImageBitmap(bitmap);
                                }
                                else
                                {
                                    if(checkTask(companyImageUrl, holder.companyThumbnail))
                                    {
                                        AsyncDrawable asyncDrawable = new AsyncDrawable(holder.companyThumbnail.getResources(),
                                                placeHolderBitmap,
                                                bitmapWorker);

                                        holder.companyThumbnail.setImageDrawable(asyncDrawable);
                                        bitmapWorker.execute(companyImageFile);
                                    }
                                }

                            } catch (JSONException e) {
                                String errorMessage = e.getMessage();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMessage = error.getMessage();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }};

            Mysingleton.getInstance(mContext).addTorequestque(stringRequest);




            holder.companyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClickedCompany.class);
                    intent.putExtra("CompanyName", mCompaniesList.get(position).getCompName());
                    intent.putExtra("CompanyType", mCompaniesList.get(position).getCompType());
                    intent.putExtra("CompanyToken", mCompaniesList.get(position).getCompToken());
                    intent.putExtra("CompanySubtype", mCompaniesList.get(position).getCompSubType());
                    intent.putExtra("CompanyId", mCompaniesList.get(position).getCompId());

//                    byte[] decodedLogo = Base64.decode(imageFile, Base64.DEFAULT);
//                    final Bitmap bitmap = BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, new BitmapFactory.Options());

//                    try {
//                        if(bitmapWorker.get() != null)
//                        BitmapHelper.getInstance().setBitmap(bitmapWorker.get());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }

                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCompaniesList.size();
    }

    // CHECK THE CURRENTLY RUNNING TASK FOR THE FILE OUR IMAGE VIEW IS EXPECTING
    private boolean checkTask(String imageFile, ImageView imageView) {
        TaskBitmapFromURL taskBitmapFromURL = getTask(imageView);

        if(taskBitmapFromURL != null)
        {
            final String workerFile = taskBitmapFromURL.getMemberImageFile();
            if(workerFile != null)
            {
                if(!workerFile.equals(imageFile))
                {
                    // 'TaskBitmapFromURL' RETURNED FILE IS NOT THE SAME AS OUR IMAGE VIEW IS EXPECTING
                    // SO CANCEL 'AsyncTask'
                    taskBitmapFromURL.cancel(true);
                }
                else
                {
                    // 'TaskBitmapFromURL' RETURNED FILE IS THE SAME AS OUR IMAGE VIEW IS EXPECTING
                    // SO DO NOTHING
                    return false;
                }
            }
        }

        return true;
    }

    public static TaskBitmapFromURL getTask(ImageView imageView)
    {
        Drawable imageDrawable = imageView.getDrawable();

        if(imageDrawable instanceof AsyncDrawable)
        {
            AsyncDrawable asyncDrawable = (AsyncDrawable) imageDrawable;

            return asyncDrawable.getTask();
        }

        return null;
    }

    static class MyCompanyViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, companyType;
        ImageView companyThumbnail;
        CardView companyCard;

        MyCompanyViewHolder(View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.main_fragment_comp_name);
            companyType = itemView.findViewById(R.id.main_fragment_comp_type);
            companyThumbnail = itemView.findViewById(R.id.main_fragment_comp_image);
            companyCard = itemView.findViewById(R.id.main_fragment_comp_card);
        }
    }
}