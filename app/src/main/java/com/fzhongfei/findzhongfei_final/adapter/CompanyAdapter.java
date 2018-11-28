package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.util.HashMap;
import java.util.List;

public class CompanyAdapter extends ArrayAdapter<Companies> {

    private Context mContext;
    public static Companies favoriteCompanyItem;
    public static ImageView companyImage;

    public CompanyAdapter(Context context, int resourceId, List<Companies> companies) {
        super(context, resourceId, companies);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        favoriteCompanyItem = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
        }

        TextView companyName = convertView.findViewById(R.id.company_names);
        TextView companyType = convertView.findViewById(R.id.company_types);
        TextView companySubType = convertView.findViewById(R.id.company_sub_types);
        companyImage = convertView.findViewById(R.id.company_images);

        if(favoriteCompanyItem != null) {
            companyName.setText(favoriteCompanyItem.getCompName());
            companyType.setText(favoriteCompanyItem.getCompType());
            companySubType.setText(favoriteCompanyItem.getCompSubType());
            companyImage.setImageBitmap(favoriteCompanyItem.getImageBitmap());

//            String compLogo = favoriteCompanyItem.getImageLogo();
//            if(compLogo != null) {
//                byte[] decodedLogo = Base64.decode(compLogo, Base64.DEFAULT);
//                companyImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
//            }

            requestImage(favoriteCompanyItem);
        }

        return convertView;
    }

    private void requestImage(Companies favoriteCompanyItem) {
        mContext = getContext();
        customStringRequest imageRequest = new customStringRequest();
        HashMap<String, String> params = new HashMap<>();

        params.put("requestType", "requestCompLogo");
        params.put("logoUrl", favoriteCompanyItem.getImageUrl());
        params.put("compToken", null);
        params.put("host", "external");

        imageRequest.setUrlPath("comp/fetchImage.php");
        imageRequest.setParams(params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(params);
        callBack.SetRequestType("requestExternalCompLogo");

        imageRequest.startConnection(mContext, callBack, params);
    }
}
