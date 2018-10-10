package com.fzhongfei.findzhongfei_final.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;
import com.fzhongfei.findzhongfei_final.utils.InternetAvailability;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity1 extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "RegisterActivity1";
    public Context mContext = RegisterActivity1.this;

    // VIEWS
    public static ProgressDialog dialog;
    private ImageButton logo, license;
    private static EditText edtCompName, edtCompCEO, edtCompPhone, edtCompEmail, edtRepName, edtRepEmail, edtPassword;
    private TextView profileTextVIew, licenseTextView;
    private Button nextRegistrationButton;

    // IMAGE FROM GALLERY
    private Uri uriSelectedImage;
    private final int requestPermissionCode = 1;
    private Bitmap logoBitmap, licenseBitmap;

    final VolleyError volleyError = new VolleyError();

    private boolean logoIsClicked, logoIsSet;
    private boolean licenseIsClicked, licenseIsSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Log.d(TAG, "onCreate: Running...");

        // PERMISSION
        final int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);

        // TOOLBAR
        setUpActivityToolbar();

        // INITIALIZING VIEWS
        edtCompName = findViewById(R.id.in_compName);
        edtCompCEO = findViewById(R.id.in_ceo);
        edtCompPhone = findViewById(R.id.in_comPhone);
        edtCompEmail = findViewById(R.id.in_compEmail);
        edtRepName = findViewById(R.id.in_repName);
        edtRepEmail = findViewById(R.id.in_repEmail);
        edtPassword = findViewById(R.id.in_compPassword);
        logo = findViewById(R.id.logoImageButton);
        license = findViewById(R.id.licenseImageButton);
        profileTextVIew = findViewById(R.id.tv_Logo);
        licenseTextView = findViewById(R.id.tv_License);
        nextRegistrationButton = findViewById(R.id.btnNextRegister1);

        // VALIDATE EDIT TEXTS
        edtCompName.addTextChangedListener(editTextTextWatcher);
        edtCompCEO.addTextChangedListener(editTextTextWatcher);
        edtCompPhone.addTextChangedListener(editTextTextWatcher);
        edtCompEmail.addTextChangedListener(editTextTextWatcher);
        edtRepName.addTextChangedListener(editTextTextWatcher);
        edtRepEmail.addTextChangedListener(editTextTextWatcher);
        edtPassword.addTextChangedListener(editTextTextWatcher);

        // DIALOG FOR REGISTERING
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setProgress(0);

        // TURN ON INTERNET
        InternetAvailability.internetIsAvailable(mContext);

        logo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//                    logoIsClicked = false;
                    RequestRuntimePermission();
                } else {
                    logoIsClicked = true;
                    openGallery();
                    Toast.makeText(mContext, "" + logoIsClicked, Toast.LENGTH_SHORT).show();
                }
            }
        });

        license.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    RequestRuntimePermission();
//                    licenseIsClicked = false;
                } else {
                    licenseIsClicked = true;
                    openGallery();
                    Toast.makeText(mContext, "" + licenseIsClicked, Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidEmail(edtRepEmail)) {
                    edtRepEmail.setError(getResources().getString(R.string.email_error));
                } else if(edtCompPhone.getText().toString().length() < 11) {
                    edtCompPhone.setError(getResources().getString(R.string.phone_error));
                } else if(!isValidEmail(edtCompEmail)) {
                    edtCompEmail.setError(getResources().getString(R.string.email_error));
                } else if(edtPassword.getText().toString().length() < 8) {
                    edtPassword.setError(getResources().getString(R.string.password_short_error));
                } else if(!isValidPassword(edtPassword)) {
                    edtPassword.setError(getResources().getString(R.string.password_invalid_characters_error));
                } else if(!logoIsSet) {
                    Toast.makeText(mContext, getResources().getString(R.string.company_profile_image_error), Toast.LENGTH_SHORT).show();
                } else if(!licenseIsSet) {
                    Toast.makeText(mContext, getResources().getString(R.string.license_image_error), Toast.LENGTH_SHORT).show();
                } else if(InternetAvailability.internetIsAvailable(mContext)) {
                    processRegistration();
                }
            }
        });
    }

    // UI - SETTING UP THE TOOLBAR
    private void setUpActivityToolbar() {
        Toolbar mToolbar;
        Window window;
        GradientDrawable mGradientDrawable;

        mGradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{0xFF5258A6, 0xFF7375B7});

        // CHANGE THE STATUS BAR COLOR TO TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(mContext.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(mContext.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(mGradientDrawable);
        }

        mToolbar = findViewById(R.id.activity_register1_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.register);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    // UI - CHECK IF EMAIL IS VALID
    private boolean isValidEmail(EditText emailInput) {
        CharSequence email = emailInput.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    private boolean isValidPassword(EditText passwordInput) {
        CharSequence password = passwordInput.getText().toString();
        boolean isValid = false;

//        Pattern PASSWORD_PATTERN
//                = Pattern.compile(
//                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}", Pattern.CASE_INSENSITIVE);
        Pattern PASSWORD_PATTERN_UPPERCASE = Pattern.compile("[A-Z ]");
        Pattern PASSWORD_PATTERN_LOWERCASE = Pattern.compile("[a-z ]");
        Pattern PASSWORD_PATTERN_DIGITS = Pattern.compile("[0-9 ]");

        if(!PASSWORD_PATTERN.matcher(password).find()) {
            return PASSWORD_PATTERN.matcher(password).find();
        } else {
            return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
        }
    }

    // UI - VALIDATION
    private TextWatcher editTextTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String compName = edtCompName.getText().toString().trim();
            String compCEO = edtCompCEO.getText().toString().trim();
            String compPhone = edtCompPhone.getText().toString().trim();
            String compEmail = edtCompEmail.getText().toString().trim();
            String repName = edtRepName.getText().toString().trim();
            String repEmail= edtRepEmail.getText().toString().trim();
            String compPassword= edtPassword.getText().toString().trim();

            nextRegistrationButton.setEnabled(
                            !compName.isEmpty() && !compCEO.isEmpty() &&
                            !compPhone.isEmpty() && !compEmail.isEmpty() &&
                            !repName.isEmpty() && !repEmail.isEmpty() && !compPassword.isEmpty());

            if(nextRegistrationButton.isEnabled()) {
                nextRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background));
            } else {
                nextRegistrationButton.setEnabled(false);
                nextRegistrationButton.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_login_background_disabled));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public static void stopConnection1() {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    // SAVE COMPANY TOKEN CACHE FOR LATER USE
    private File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile("token:", null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    // FORM FILLED
    @TargetApi(Build.VERSION_CODES.O)
    private void processRegistration() {
        final String comp_name, comp_Phone, comp_Email, comp_CEO, rep_Name, rep_Email;
        customStringRequest registerRequest = new customStringRequest("register/index.php");

        dialog.show();

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    Toast.makeText(mContext, "Process is taking longer than usual please check your internet connection", Toast.LENGTH_SHORT)
                            .show();
                } else if(volleyError.getMessage() != null && dialog.isShowing()) {
                    Toast.makeText(mContext, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {

                }
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 100 * 300);

        comp_name = edtCompName.getText().toString();
        comp_Phone = edtCompPhone.getText().toString();
        comp_Email = edtCompEmail.getText().toString();
        comp_CEO = edtCompCEO.getText().toString();
        rep_Name = edtRepName.getText().toString();
        rep_Email = edtRepEmail.getText().toString();

        // VOLLEY STRING REQUEST TO SEND USER DATA TO SERVER
        byte[] logoBytes = imageToString(logoBitmap).getBytes();
        byte[] licenseBytes = imageToString(licenseBitmap).getBytes();
        String logoSignature = null;
        String licenseSignature = null;

        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            MD5.update(logoBytes);
            BigInteger logoHash = new BigInteger(1, MD5.digest());
            MD5.update(licenseBytes);
            BigInteger licenseHash = new BigInteger(1, MD5.digest());

            logoSignature = logoHash.toString(16);
            licenseSignature = licenseHash.toString(16);
            Log.d(TAG, "processRegistration: LOGO SIGNATURE " + logoSignature);
            Log.d(TAG, "processRegistration: LICENSE SIGNATURE " + licenseSignature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(TAG, "processRegistration: NOT VALID ALGORITHM");
        }

        HashMap<String, String> Params = new HashMap<String, String>();

        Params.put("comp_name", comp_name);
        Params.put("comp_ceo", comp_CEO);
        Params.put("comp_repName", rep_Name);
        Params.put("comp_repEmail", rep_Email);
        Params.put("comp_phone", comp_Phone);
        Params.put("comp_email", comp_Email);
        Params.put("comp_logo", imageToString(logoBitmap));
        Params.put("comp_license", imageToString(licenseBitmap));
        Params.put("comp_logoSignature", logoSignature);
        Params.put("comp_licenseSignature", licenseSignature);

        Log.d(TAG, "processRegistration: logoSignature************************************" + logoSignature);
        Log.d(TAG, "processRegistration: licenseSignature*********************************" + licenseSignature);

        registerRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        registerRequest.startConnection(mContext, callBack, Params);

        if (!callBack.isSuccess()) {
            String errorMessage = callBack.getErrorMessage();
            if(errorMessage != null) {
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
            }

            Log.d(TAG, "NEUD===========================processRegistration: is success is: " + errorMessage);
            Log.d(TAG, "NEUD===========================processRegistration: success message after failure: " + callBack.getSuccessMessage());
        } else {
            Toast.makeText(mContext, (callBack.getSuccessMessage()), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "NEUD===========================processRegistration: success message " + callBack.getSuccessMessage());
        }
    }

    // REQUEST USER PERMISSION TO SELECT AN IMAGE FROM GALLERY
    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity1.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(mContext, "Let the app use your gallery", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(RegisterActivity1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestPermissionCode);
        } else {
            ActivityCompat.requestPermissions(RegisterActivity1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestPermissionCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestPermissionCode: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();
                    openGallery();
                    Log.d(TAG, "onRequestPermissionsResult: LICENSE IS CLICKED____________________________" + licenseIsClicked);
                    Log.d(TAG, "onRequestPermissionsResult: LOGO IS CLICKED____________________________" + logoIsClicked);
//                    if(licenseIsClicked){
//                        licenseIsClicked = false;
//                    }
//                    if(!logoIsClicked) {
//                        logoIsClicked = true;
//                    }
//                } else {
//                    Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    // OPEN GALLERY TO PICK AN IMAGE
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void openGallery() {
        Intent galleryIntent;
        galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(galleryIntent, "Please select an image"), 2);
    }

    // AFTER USER SELECTED AN IMAGE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            performCrop();
        } else if (requestCode == 2) {
            if(imageReturnedIntent != null) {
                uriSelectedImage = imageReturnedIntent.getData();
                performCrop();
            }
        } else if(requestCode == 1) {
            if (imageReturnedIntent != null) {
//                Bundle bundle = imageReturnedIntent.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");

//                Bitmap logoBitmap = ((BitmapDrawable) logo.getDrawable()).getBitmap();
//                Bitmap licenseBitmap = ((BitmapDrawable) license.getDrawable()).getBitmap();

                if(logoIsClicked) {
                    String logoPath = Environment.getExternalStorageDirectory() + "/temporary_holder.jpg";
                    logoBitmap = BitmapFactory.decodeFile(logoPath);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.drawable.logo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    logoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] logoInByte = stream.toByteArray();
                    long logoLengthBmp = logoInByte.length;

                    logo.setImageBitmap(logoBitmap);
                    profileTextVIew.setVisibility(View.GONE);
                    logoIsSet = true;
                    logoIsClicked = false;

                } else if(licenseIsClicked) {
                    String licensePath = Environment.getExternalStorageDirectory() + "/temporary_holder.jpg";
                    licenseBitmap = BitmapFactory.decodeFile(licensePath);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            R.drawable.logo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    licenseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] logoInByte = stream.toByteArray();
                    long licenceLengthBmp = logoInByte.length;

                    license.setImageBitmap(licenseBitmap);
                    licenseTextView.setVisibility(View.GONE);
                    licenseIsSet = true;
                    licenseIsClicked = false;
                }
            }
        }
    }

    // CROPPING THE IMAGES IE. PROFILE AND LICENSE
    private void performCrop() {
        Intent cropIntent;
        try {
            cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriSelectedImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate output X and Y
            cropIntent.putExtra("outputX", 800);
            cropIntent.putExtra("outputY", 1000);
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 4);
            cropIntent.putExtra("aspectY", 5);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra("scaleUpIfNeeded", true);

            File f = new File(Environment.getExternalStorageDirectory(),
                    "/temporary_holder.jpg");
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Log.e("io", ex.getMessage());
            }

            uriSelectedImage = Uri.fromFile(f);

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSelectedImage);

            // START THE ACTIVITY - HANDLE RETURNING IN onActivityResult
            startActivityForResult(cropIntent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Your device doesn't support image cropping", Toast.LENGTH_SHORT).show();
        }
    }

    // CHANGING THE IMAGE BITMAP TO STRING
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // COMPRESS THE BITMAP INTO JPEG FORMAT
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } catch (Exception e) {
//            Log.d(TAG, "NEUD===========================imageToString: " + e.toString());
        }

        //  THE IMAGE IS INSIDE THE OUTPUT STREAM - CONVERT IT TO BYTES
        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        // IMAGE IS INSIDE BYTE ARRAY
        // ENCODING BYTES INTO STRINGS
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    // GETTER FOR COMPANY PROFILE
    public static String[] getCompanyFields() {
        return new String[]{edtCompName.getText().toString(), edtCompPhone.getText().toString(), edtCompEmail.getText().toString(),
                edtCompCEO.getText().toString(), edtRepName.getText().toString(), edtRepEmail.getText().toString()};
    }
}












// UPLOAD IMAGE AS SOON AS THE USER SELECTS AN IMAGE
//    new UploadImage(mContext, logoBitmap).execute();



//    private static class UploadImage extends AsyncTask<Void, Void, JSONObject> {
//        Bitmap image;
////        public AsyncResponse delegate = null;
//        Context context;
//
//        // THE IMAGE SELECTED
//        private UploadImage(Context context, Bitmap image) {
//            this.image = image;
//            this.context = context;
////            this.delegate = delegate;
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... voids) {
//            JSONObject jsonObject = new JSONObject();
//
//            // ENCODE THE IMAGE TO BASE 64
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//
//            // LIST OF THINGS SENDING TO THE SERVER
//            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
//            dataToSend.add(new BasicNameValuePair("comp_logo", encodedImage));
//
//            HttpParams httpRequestParams = getHttpRequestParams();
//            HttpClient client = new DefaultHttpClient(httpRequestParams);
//            HttpPost post = new HttpPost(MainActivity.SERVER_URL + "register/index.php");
//
//            try {
//                post.setEntity(new UrlEncodedFormEntity(dataToSend));
//                client.execute(post);
//
//                HttpResponse response = client.execute(post);
//                // CHECK IF WE HAVE RESPONSE FORM SERVER
//                int status = response.getStatusLine().getStatusCode();
//
//                // 200 -> SUCCESSFULLY RECEIVED SOME DATA
//                if(status == 200) {
//                    // GET THE RESPONSE AND MAKE IT A STRING
//                    Log.d(TAG, "doInBackground: STATUS ++++++++++++++++++++++++++" + status);
//                    HttpEntity entity = response.getEntity();
//                    String data = EntityUtils.toString(entity);
//
////                    HttpParams entity = response.getParams();
//                    Log.d(TAG, "doInBackground: PARAMS: " + data);
//
//                    jsonObject = new JSONObject(data);
////                    JSONArray jsonArray = jsonObject.getJSONArray("success");
//                }
//            } catch(ClientProtocolException e) {
//                e.printStackTrace();
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//
//            Log.d(TAG, "doInBackground: RESPONSE::" + jsonObject);
//            return jsonObject;
//        }
//
////        public interface AsyncResponse {
////            // DATA WE LIKE TO RETURN FROM ASYNC
////            JSONObject processFinish(JSONObject response);
////        }
//
//        // WHEN THE CODE FINISHES RUNNING
//        @Override
//        protected void onPostExecute(JSONObject response) {
////            delegate.processFinish(response);
////            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    // GAINING CONNECTION WITH THE SERVER
//    private static HttpParams getHttpRequestParams() {
//        HttpParams httpRequestParams = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 30 * 1000);
//        HttpConnectionParams.setSoTimeout(httpRequestParams, 30 * 1000);
//
//        return httpRequestParams;
//    }

