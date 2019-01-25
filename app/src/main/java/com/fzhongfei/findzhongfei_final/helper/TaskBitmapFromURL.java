package com.fzhongfei.findzhongfei_final.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.BitmapCache;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.ImageCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fzhongfei.findzhongfei_final.adapter.MainFragmentCompaniesRecyclerViewAdapter;
import com.fzhongfei.findzhongfei_final.constants.Constants;
import com.fzhongfei.findzhongfei_final.fragments.MainFragment1;
import com.fzhongfei.findzhongfei_final.model.Companies;
import com.fzhongfei.findzhongfei_final.model.CompanyProfile;
import com.fzhongfei.findzhongfei_final.server.Mysingleton;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.fzhongfei.findzhongfei_final.server.callBackImplement.TAG;

public class TaskBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<ImageView> mImageViewWeakReference;
    private WeakReference<Context> mContext;
    private String companyToken;
    private String memberImageFile;
    private int requiredImageViewWidth;
    private int requiredImageViewHeight;

    // GARBAGE COLLECTION
    public TaskBitmapFromURL(Context context, ImageView imageView, String companyToken, int imageViewWidth, int imageViewHeight)
    {
        mContext = new WeakReference<>(context);
        mImageViewWeakReference = new WeakReference<>(imageView);
        this.companyToken = companyToken;
        this.requiredImageViewWidth = imageViewWidth;
        this.requiredImageViewHeight = imageViewHeight;
    }

    @Override
    protected Bitmap doInBackground(String... imageUrls) {
//        try
//        {
//            InputStream inputStream = new URL(imageUrls[0]).openStream();
//            return BitmapFactory.decodeStream(inputStream);
//        }
//        catch (IOException ex)
//        {
//            return null;
//        }
//        HttpURLConnection connection = null;
//
//        try
//        {
//            URL url = new URL(imageUrls[0]);
//            // Initialize a new http url connection
//            connection = (HttpURLConnection) url.openConnection();
//
//            // Connect the http url connection
//            connection.connect();
//
//            // Get the input stream from http url connection
//            InputStream inputStream = connection.getInputStream();
//
//                /*
//                    BufferedInputStream
//                        A BufferedInputStream adds functionality to another input stream-namely,
//                        the ability to buffer the input and to support the mark and reset methods.
//                */
//                /*
//                    BufferedInputStream(InputStream in)
//                        Creates a BufferedInputStream and saves its argument,
//                        the input stream in, for later use.
//                */
//            // Initialize a new BufferedInputStream from InputStream
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//
//                /*
//                    decodeStream
//                        Bitmap decodeStream (InputStream is)
//                            Decode an input stream into a bitmap. If the input stream is null, or
//                            cannot be used to decode a bitmap, the function returns null. The stream's
//                            position will be where ever it was after the encoded data was read.
//
//                        Parameters
//                            is InputStream : The input stream that holds the raw data
//                                              to be decoded into a bitmap.
//                        Returns
//                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
//                */
//            // Convert BufferedInputStream to Bitmap object
//            return BitmapFactory.decodeStream(bufferedInputStream);
//
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            // Disconnect the http url connection
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//        return null;

//        memberImageFile = imageUrls[0];
//        Bitmap bitmap = decodeBitmapFromUrl(memberImageFile);
//        if(bitmap != null)
//        {
//            MainFragment1.setBitmapInMemoryCache(memberImageFile, bitmap);
//        }

        memberImageFile = imageUrls[0];
        Bitmap bitmap = decodeBitmapFromImageFile(memberImageFile);

        if(bitmap != null)
        {
            MainFragment1.setBitmapInMemoryCache(memberImageFile, bitmap);
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        if(bitmap != null && mImageViewWeakReference != null)
//        {
//            ImageView imageView = mImageViewWeakReference.get();
//
//            if(imageView != null)
//            {
//                imageView.setImageBitmap(bitmap);
//            }
//        }

        // CHECK IF PROCESS IS GETTING CANCELED AND RETURNING WHAT THE IMAGE VIEW WHATS
        if(isCancelled())
        {
            bitmap = null;
        }

        if(bitmap != null && mImageViewWeakReference != null)
        {
            ImageView imageView = mImageViewWeakReference.get();
            TaskBitmapFromURL getTask = MainFragmentCompaniesRecyclerViewAdapter.getTask(imageView);

            if(this == getTask && imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options bitmapOptions, int REQUIRED_IMAGE_VIEW_WIDTH, int REQUIRED_IMAGE_VIEW_HEIGHT)
    {
        // RAW HEIGHT AND WIDTH OF IMAGE
        final int PHOTO_WIDTH = bitmapOptions.outWidth;
        final int PHOTO_HEIGHT = bitmapOptions.outHeight;

        // SCALE OF RESIZED BITMAP AND IMAGE VIEW
        int scaleFactor = 1;

        if(PHOTO_WIDTH > REQUIRED_IMAGE_VIEW_WIDTH || PHOTO_HEIGHT > REQUIRED_IMAGE_VIEW_HEIGHT)
        {
            final int halfPhotoWidth = PHOTO_WIDTH / 2;
            final int halfPhotoHeight = PHOTO_HEIGHT / 2;

            // CALCULATE THE LARGEST 'scaleFactor' VALUE THAT IS A POWER OF 2 AND KEEPS BOTH THE HEIGHT AND WIDTH LARGER THAN THE REQUESTED SIZE
            while(  halfPhotoWidth / scaleFactor > REQUIRED_IMAGE_VIEW_WIDTH ||
                    halfPhotoHeight / scaleFactor > REQUIRED_IMAGE_VIEW_HEIGHT)
            {
                scaleFactor *= 2;
            }
        }

        return scaleFactor;
    }

//    private Bitmap decodeBitmapFromServer(String imageUrl)
//    {
//        customStringRequest imageRequest = new customStringRequest();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//
//        // READ FILE WITHOUT LOADING THE BITMAP TO MEMORY TO GET WITH AND HEIGHT
//        options.inJustDecodeBounds = true;
//
//        HashMap<String, String> params = new HashMap<>();
//
//        params.put("requestType", "requestCompLogo");
//        params.put("logoUrl", imageUrl);
////            params.put("compToken", companyTokenValue);
//
//        imageRequest.setUrlPath("comp/fetchImage.php");
//        imageRequest.setParams(params);
//
//        callBackImplement callBack = new callBackImplement(mContext.get());
//        callBack.setParams(params);
//        callBack.SetRequestType("requestCompLogo");
//
//        imageRequest.startConnection(mContext.get(), callBack, params);
//
//        // SENDING PHOTO INFORMATION TO CALCULATE AND RESIZE
//        options.inSampleSize = calculateInSampleSize(options, imageViewWidth, imageViewHeight);
//        // LOADING THE ACTUAL BITMAP
//        options.inJustDecodeBounds = false;
//        // ALLOW ANDROID TO CLAIM THE BITMAP MEMORY IF IT RUNS LOW ON MEMORY
//        options.inPurgeable = true;
//        options.inInputShareable = true;
//        options.inTempStorage = new byte[16 * 1024];
//
//        return BitmapFactory.decodeStream(bufferedInputStream, null, options);
//    }

    private Bitmap decodeBitmapFromUrl(String imageUrl)
    {
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();

            // READ FILE WITHOUT LOADING THE BITMAP TO MEMORY TO GET WITH AND HEIGHT
            options.inJustDecodeBounds = true;

            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            connection.connect();

            // GET THE INPUT STREAM FROM HTTP URL CONNECTION
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            inputStream.mark(inputStream.available());

            // POPULATE 'options' OBJECT WITH THE PHOTO WIDTH AND HEIGHT
            BitmapFactory.decodeStream(inputStream, null, options);

            // SENDING PHOTO INFORMATION TO CALCULATE AND RESIZE
            options.inSampleSize = calculateInSampleSize(options, requiredImageViewWidth, requiredImageViewHeight);
            // LOADING THE ACTUAL BITMAP
            options.inJustDecodeBounds = false;
            // ALLOW ANDROID TO CLAIM THE BITMAP MEMORY IF IT RUNS LOW ON MEMORY
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            // WE CAN'T USE THE INPUT STREAM AGAIN SO MUST BE DECLARED AGAIN
            inputStream.reset();

            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getMemberImageFile()
    {
        return memberImageFile;
    }

    private Bitmap decodeBitmapFromImageFile(String imageFile)
    {
        byte[] decodedLogo = Base64.decode(imageFile, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length);
    }

//    private Bitmap bitmap;
//
//    private Bitmap decodeBitmapFromServer(final Context context, String imageUrl, String companyToken) {
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(context);
//        final String requestUrl = Constants.SERVER_URL + "comp/fetchImage.php";
//        final HashMap<String, String> params = new HashMap<>();
//
//        params.put("requestType", "requestCompLogo");
//        params.put("logoUrl", imageUrl);
//        params.put("compToken", companyToken);
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject responseArray = new JSONObject(response);
//
//                            byte[] decodedLogo = Base64.decode(responseArray.get("imageFile").toString(), Base64.DEFAULT);
//                            bitmap = BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length);
//                        } catch (JSONException e) {
//                            String errorMessage = e.getMessage();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String errorMessage = error.getMessage();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                return params;
//            }};
//
//        Mysingleton.getInstance(context).addTorequestque(stringRequest);
//
//        // Add the request to the RequestQueue.
////        queue.add(stringRequest);
//
//        return bitmap;
//    }

//    private Bitmap decodeBitmapFromString(String imageFile)
//    {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//
//        options.inJustDecodeBounds = true;
//
//        byte[] decodedLogo = Base64.decode(imageFile, Base64.DEFAULT);
//        BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, options);
//
//        options.inSampleSize = calculateInSampleSize(options, 200, 200);
//        // LOADING THE ACTUAL BITMAP
//        options.inJustDecodeBounds = false;
//        options.inPurgeable = true;
//        options.inInputShareable = true;
//        options.inTempStorage = new byte[16 * 1024];
//
//        return BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length, options);
//    }
}
