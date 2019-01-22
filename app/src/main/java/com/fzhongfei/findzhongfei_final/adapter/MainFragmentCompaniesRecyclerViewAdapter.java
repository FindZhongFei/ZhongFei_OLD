package com.fzhongfei.findzhongfei_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.activity.ClickedCompany;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.utils.BitmapHelper;

import java.util.List;

public class MainFragmentCompaniesRecyclerViewAdapter extends RecyclerView.Adapter<MainFragmentCompaniesRecyclerViewAdapter.MyCompanyViewHolder> {

    private Context mContext;
    private List<Companies> mCompaniesList;

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
        String imageFile = company.getImageLogo();
        Drawable imageDrawable = mContext.getResources().getDrawable(R.drawable.loading_image_background);
        Drawable textDrawable = mContext.getResources().getDrawable(R.drawable.loading_text_background);
        LinearLayout.LayoutParams companyTextParams;
        RelativeLayout.LayoutParams imageParams;

        if(company.getImageLogo() == null)
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
            holder.companyName.setText(company.getCompName());
            holder.companyType.setText(company.getCompType());

            // Decode image (without loading it in memory) to get its size
            // The size will be used to calculate a sample size used in decode the image
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = false;
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            String imageType = options.outMimeType;
            options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight);

            byte[] decodedLogo = Base64.decode(imageFile, Base64.DEFAULT);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, options);

            if(bitmap != null) {
                float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                int width = bitmap.getWidth();
                int height = Math.round(width / aspectRatio);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

//            byte[] decodedLogo = Base64.decode(company.getImageLogo(), Base64.DEFAULT);
                holder.companyThumbnail.setImageBitmap(resizedBitmap);
            }

            holder.companyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClickedCompany.class);
                    intent.putExtra("CompanyName", mCompaniesList.get(position).getCompName());
                    intent.putExtra("CompanyType", mCompaniesList.get(position).getCompType());
                    intent.putExtra("CompanyToken", mCompaniesList.get(position).getCompToken());
                    intent.putExtra("CompanySubtype", mCompaniesList.get(position).getCompSubType());
                    intent.putExtra("CompanyId", mCompaniesList.get(position).getCompId());

                    BitmapHelper.getInstance().setBitmap(bitmap);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCompaniesList.size();
    }

    public void swap(List<Companies> company)
    {
        if(company == null || company.size() == 0)
        {
            return;
        }
        else if(mCompaniesList != null && mCompaniesList.size() > 0)
        {
            mCompaniesList.clear();
        }
        assert mCompaniesList != null;
        mCompaniesList.addAll(company);
        notifyDataSetChanged();
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
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

    /*
     * returns the thumbnail image bitmap from the given url
     *
     * @param path
     * @param thumbnailSize
     *
     */
    private Bitmap getThumbnailBitmap(final String path, final int thumbnailSize) {
        Bitmap bitmap;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            bitmap = null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }
}