package com.fzhongfei.findzhongfei_final.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.adapter.MultipleImagesAdapter;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentBottomSheetPostStatus extends DialogFragment {

    private static final String TAG = "FragmentBottomSheetPost";
    Context mContext;

    // VIEWS
    View view;
    EditText input;
    private GridView gvGallery;
    private MultipleImagesAdapter galleryAdapter;

    // VALUES
    boolean withImages, withVideo;
    CompanyProfile mCompanyProfile;

    String statusInput;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    List<Uri> mArrayUri = new ArrayList<>();
    List<Bitmap> mArrayBitmap = new ArrayList<>();

    public FragmentBottomSheetPostStatus()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_status, container, false);

        mContext = getActivity();

        mCompanyProfile = new CompanyProfile(mContext);
        mCompanyProfile.setPropertiesFromSharePreference(mContext);

        input = view.findViewById(R.id.post_status_edit_text);
        ImageButton close = view.findViewById(R.id.full_screen_dialog_button_close);
        ImageButton addImages = view.findViewById(R.id.add_images_to_post_button);
        gvGallery = view.findViewById(R.id.grid_view);
        Button post = view.findViewById(R.id.full_screen_dialog_button_post);

        input.addTextChangedListener(editTextTextWatcher);

        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).onBackPressed();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customStringRequest registerRequest = new customStringRequest("comp/addstatus.php");

                HashMap<String, String> Params = new HashMap<>();

                Params.put("action", "newStatus");
                Params.put("comp_token", mCompanyProfile.getCompanyToken());
                Params.put("status_text", statusInput);

                if(withImages)
                {
                    Params.put("containsImages", "true");
                    Params.put("images", "will send you images here");
                }
                else
                {
                    Params.put("containsImages", "false");
                }

                if(withVideo)
                {
                    Params.put("containsVideos", "true");
                    Params.put("images", "will send you video here");
                }
                else
                {
                    Params.put("containsVideos", "false");
                }

                registerRequest.setParams(Params);

                callBackImplement callBack = new callBackImplement(mContext);
                callBack.setParams(Params);
                callBack.SetRequestType("comp_status");
                registerRequest.startConnection(mContext, callBack, Params);
            }
        });

        return view;
    }

    // UI - VALIDATION
    private TextWatcher editTextTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            statusInput = input.getText().toString().trim();
        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    };

    private void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
//        Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(chooseIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try
        {
            // When an Image is picked
            if(requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data)
            {
                // Get the Image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<>();
                if(data.getData()!=null)
                {

                    Uri selectedImage = data.getData();
                    String stringImagePath = selectedImage.getPath();



//                    // Get the cursor
//                    Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imageEncoded  = cursor.getString(columnIndex);
//                    cursor.close();

//                    Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnails(
//                            mContext.getContentResolver(), selectedImage,
//                            MediaStore.Images.Thumbnails.MINI_KIND,
//                            null );
//
//                    if( cursor != null && cursor.getCount() > 0 ) {
//                        cursor.moveToFirst();//**EDIT**
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        Toast.makeText(mContext, "HERE", Toast.LENGTH_SHORT).show();
//                        imageEncoded = cursor.getString( columnIndex );
//                        cursor.close();
//                    }

                    imageEncoded = getRealPathFromURI(selectedImage);

//                    mArrayUri.add(selectedImage);

                    mArrayBitmap.add(decodeBitmapFromURI(selectedImage, mContext));
//                    galleryAdapter = new MultipleImagesAdapter(mContext, mArrayUri);
                    galleryAdapter = new MultipleImagesAdapter(mContext, mArrayBitmap);
                    gvGallery.setVisibility(View.VISIBLE);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                }
                else
                {
                    if(data.getClipData() != null)
                    {
                        ClipData mClipData = data.getClipData();

                        for(int i = 0; i < mClipData.getItemCount(); i++)
                        {
//                            // Get the cursor
//                            Cursor cursor = mContext.getContentResolver().query(uri, filePathColumn, null, null, null);
//                            // Move to first row
//                            cursor.moveToFirst();
//
//                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                            imageEncoded  = cursor.getString(columnIndex);
//                            imagesEncodedList.add(imageEncoded);
//                            cursor.close();

                            if(mArrayBitmap.size() >= 4)
                            {
                                Toast.makeText(mContext, "Please select maximum of 4 images", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();

                                mArrayBitmap.add(decodeBitmapFromURI(uri, mContext));
                                imageEncoded = getRealPathFromURI(uri);

                                galleryAdapter = new MultipleImagesAdapter(mContext, mArrayBitmap);
                                gvGallery.setVisibility(View.VISIBLE);
                                gvGallery.setAdapter(galleryAdapter);
                                gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                                mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                                Log.d(TAG, "onActivityResult: ");
                            }
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            }
            else
            {
                Toast.makeText(mContext, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "Something went wrong\n" + e.toString(), Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {

        String result;
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = mContext.getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor == null)
        { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(filePathColumn[0]);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }

    final static int TARGET_IMAGE_VIEW_WIDTH = 250;
    final static int TARGET_IMAGE_VIEW_HEIGHT = 250;

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    private int calculateInSampleSize(BitmapFactory.Options bitmapOptions)
    {
        final int PHOTO_WIDTH = bitmapOptions.outWidth;
        final int PHOTO_HEIGHT = bitmapOptions.outHeight;

        // SCALE OF RESIZED BITMAP AND IMAGE VIEW
        int scaleFactor = 1;

        if(PHOTO_WIDTH > TARGET_IMAGE_VIEW_WIDTH || PHOTO_HEIGHT > TARGET_IMAGE_VIEW_HEIGHT)
        {
            final int halfPhotoWidth = PHOTO_WIDTH / 2;
            final int halfPhotoHeight = PHOTO_HEIGHT / 2;

            while(  halfPhotoWidth / scaleFactor > TARGET_IMAGE_VIEW_WIDTH ||
                    halfPhotoHeight / scaleFactor > TARGET_IMAGE_VIEW_HEIGHT)
            {
                scaleFactor *= 2;
            }
        }

        return scaleFactor;
    }

    private Bitmap decodeBitmapFromURI(Uri imageFile, Context context) throws IOException
    {
        InputStream input = context.getContentResolver().openInputStream(imageFile);
        BitmapFactory.Options options = new BitmapFactory.Options();

        // DUMMY BITMAP TO CALCULATE DESIRED WIDTH AND HEIGHT
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(input, null, options);
        if(input != null)
        {
            input.close();
        }

        options.inSampleSize = calculateInSampleSize(options);
        // LOADING THE ACTUAL BITMAP
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        input = context.getContentResolver().openInputStream(imageFile);
        return BitmapFactory.decodeStream(input, null, options);
    }
}
