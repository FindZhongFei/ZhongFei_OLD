package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.FavoriteCompany;

import java.util.List;

public class FavoriteCompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FavoriteCompany> mCompanies;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore mILoadMore;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public FavoriteCompanyAdapter(RecyclerView recyclerView, Context context, List<FavoriteCompany> companies) {
        this.mContext = context;
        this.mCompanies = companies;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
                {
                    if(mILoadMore != null)
                    {
                        mILoadMore.onLoadMore();
                    }

                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mCompanies.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setILoadMore(ILoadMore iLoadMore) {
        this.mILoadMore = iLoadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_favorites_list, parent, false);
            return new CompanyViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_view, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CompanyViewHolder)
        {
            FavoriteCompany favoriteCompany = mCompanies.get(position);
            CompanyViewHolder viewHolder = (CompanyViewHolder) holder;

            viewHolder.companyImageView.setImageDrawable(mContext.getResources().getDrawable(favoriteCompany.getImage()));

            viewHolder.txtCompName.setText(favoriteCompany.getCompName());
            viewHolder.txtCompType.setText(favoriteCompany.getCompType());
            viewHolder.txtCompDesc.setText(favoriteCompany.getShortDesc());
            viewHolder.txtRating.setText(String.valueOf(favoriteCompany.getRating()));
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.mProgressBar.setIndeterminate(true);

        }
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    public void setLoading() {
        isLoading = false;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar mProgressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.main_progress_bar);
        }
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

