package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.ClickedCompany;
import com.fzhongfei.findzhongfei_final.model.Companies;

import java.util.List;

public class MainFragmentCompaniesRecyclerView extends RecyclerView.Adapter<MainFragmentCompaniesRecyclerView.MyCompanyViewHolder> {

    private Context mContext;
    public static List<Companies> mCompaniesList;

    public MainFragmentCompaniesRecyclerView(Context context, List<Companies> companiesList) {
        mContext = context;
        mCompaniesList = companiesList;
    }

    @NonNull
    @Override
    public MyCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_fragment_cardview_companies, null);

        return new MyCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCompanyViewHolder holder, final int position) {
        holder.companyName.setText(mCompaniesList.get(position).getCompName());
        holder.companyType.setText(mCompaniesList.get(position).getCompType());
        holder.companyThumbnail.setImageBitmap(mCompaniesList.get(position).getCompanyImageBitmap());

        holder.companyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClickedCompany.class);
                intent.putExtra("CompanyName", mCompaniesList.get(position).getCompName());
                intent.putExtra("CompanyType", mCompaniesList.get(position).getCompType());
                intent.putExtra("CompanyImage", mCompaniesList.get(position).getCompanyImageBitmap());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCompaniesList.size();
    }

    public static class MyCompanyViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, companyType;
        ImageView companyThumbnail;
        CardView companyCard;

        public MyCompanyViewHolder(View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.main_fragment_comp_name);
            companyType = itemView.findViewById(R.id.main_fragment_comp_type);
            companyThumbnail = itemView.findViewById(R.id.main_fragment_comp_image);
            companyCard = itemView.findViewById(R.id.main_fragment_comp_card);
        }
    }
}
