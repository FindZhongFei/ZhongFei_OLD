package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.FavoriteCompany;

import java.util.List;

public class FavoriteCompanyAdapter extends RecyclerView.Adapter<FavoriteCompanyAdapter.CompanyViewHolder> {

    private Context mContext;
    private List<FavoriteCompany> mCompanies;

    public FavoriteCompanyAdapter(Context context, List<FavoriteCompany> companies) {
        mContext = context;
        mCompanies = companies;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_favorites_list, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        FavoriteCompany favoriteCompany = mCompanies.get(position);

        holder.companyImageView.setImageDrawable(mContext.getResources().getDrawable(favoriteCompany.getImage()));

        holder.txtCompName.setText(favoriteCompany.getCompName());
        holder.txtCompType.setText(favoriteCompany.getCompType());
        holder.txtCompDesc.setText(favoriteCompany.getShortDesc());
        holder.txtRating.setText(String.valueOf(favoriteCompany.getRating()));
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }


    class CompanyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyImageView;
        TextView txtCompName, txtCompType, txtCompDesc, txtRating;

        public CompanyViewHolder(View itemView) {
            super(itemView);

            companyImageView = itemView.findViewById(R.id.compImageView);
            txtCompName = itemView.findViewById(R.id.compNameView);
            txtCompType = itemView.findViewById(R.id.compTypeView);
            txtCompDesc = itemView.findViewById(R.id.shortDescView);
            txtRating = itemView.findViewById(R.id.viewRatingView);
        }
    }
}

