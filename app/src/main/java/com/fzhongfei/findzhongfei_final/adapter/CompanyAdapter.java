package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.Companies;

import java.util.List;

public class CompanyAdapter extends ArrayAdapter<Companies> {

    private Context mContext;

    public CompanyAdapter(Context context, int resourceId, List<Companies> companies) {
        super(context, resourceId, companies);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Companies favoriteCompanyItem = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
        }

        TextView companyName = convertView.findViewById(R.id.company_names);
        TextView companyType = convertView.findViewById(R.id.company_types);
        ImageView companyImage = convertView.findViewById(R.id.company_images);

        companyName.setText(favoriteCompanyItem.getCompName());
        companyType.setText(favoriteCompanyItem.getCompType());
        companyImage.setImageResource(R.drawable.profile_picture);

        return convertView;
    }
}
