package com.fzhongfei.findzhongfei_final.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fzhongfei.findzhongfei_final.R;
import com.fzhongfei.findzhongfei_final.model.UserProfile;
import com.fzhongfei.findzhongfei_final.server.callBackImplement;
import com.fzhongfei.findzhongfei_final.server.customStringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UserProfileEditActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "UserProfileEditActivity";
    private Context mContext = UserProfileEditActivity.this;

    // VIEWS
    private TextView userFirstName, userLastName, userPhoneNumber, userEmail, signOutText;
    private TextView firstNameTitle, lastNameTitle, phoneNumberTitle, emailTitle;
    public static ImageView editProfilePicture;
    public static ProgressDialog mProgressDialog;

    public static Context sContext;

    // IMAGE FROM GALLERY
    private Uri uriSelectedImage;
    private Bitmap profilePictureBitmap;
    private final int requestPermissionCode = 1;

    UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        Log.d(TAG, "onCreate: Running...");

        sContext = getApplicationContext();

        mUserProfile = new UserProfile(mContext);
        mUserProfile.setPropertiesFromSharePreference(mContext);

        // INITIALIZING VIEWS
        TextView signOutButton = findViewById(R.id.sign_out_button);

        LinearLayout profilePictureLayout = findViewById(R.id.user_profile_edit_profile_pic_linear);
        LinearLayout firstNameLayout = findViewById(R.id.user_profile_edit_first_name_linear);
        LinearLayout lastNameLayout = findViewById(R.id.user_profile_edit_last_name_linear);
        LinearLayout phoneLayout = findViewById(R.id.user_profile_edit_phone_linear);
        LinearLayout emailLayout = findViewById(R.id.user_profile_edit_email_linear);

        firstNameTitle = findViewById(R.id.user_profile_edit_first_name_text);
        lastNameTitle = findViewById(R.id.user_profile_edit_last_name_text);
        phoneNumberTitle = findViewById(R.id.user_profile_edit_phone_text);
        emailTitle = findViewById(R.id.user_profile_edit_email_text);

        userFirstName = findViewById(R.id.user_profile_edit_first_name);
        userLastName = findViewById(R.id.user_profile_edit_last_name);
        userPhoneNumber = findViewById(R.id.user_profile_edit_phone_number);
        userEmail = findViewById(R.id.user_profile_edit_email);
        editProfilePicture = findViewById(R.id.edit_profile_picture);
        signOutText = findViewById(R.id.user_profile_signout_text);


        // TOOLBAR
        setUpActivityToolbar();

        // SHOW PROFILE DETAILS
        showProfileDetails();

        // PERMISSION
        final int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);

        // SIGN OUT
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });

        profilePictureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                        RequestRuntimePermission();
                    } else {
                        openGallery();
                    }
                }
            }
        });
        firstNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firstNameTitle, userFirstName, "userFirstName");
            }
        });
        lastNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(lastNameTitle, userLastName, "userLastName");
            }
        });
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(phoneNumberTitle, userPhoneNumber, "userPhoneNumber");
            }
        });
        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(emailTitle, userEmail, "userEmail");
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
                new int[] {0xFF5258A6,0xFF7375B7});

        // CHANGE THE STATUS BAR COLOR TO TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(mContext.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(mContext.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(mGradientDrawable);
        }

        mToolbar = findViewById(R.id.activity_user_profile_edit_toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setTitleMarginStart(-70);
        mToolbar.setBackground(mGradientDrawable);
        mToolbar.setTitle(R.string.profileTitle);
    }

    // UI - TOOLBAR BACK BUTTON
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // SHOW PROFILE DETAILS
    private void showProfileDetails() {
        UserProfile userProfile = new UserProfile(mContext);
        userProfile.setPropertiesFromSharePreference(mContext);

        String userProfilePicValue = userProfile.getUserProfilePicture();
        String userFirstNameValue = userProfile.getUserFirstName();
        String userLastNameValue = userProfile.getUserLastName();
        String userPhoneNumberValue = "+" + userProfile.getUserPhone();
        String userEmailValue = userProfile.getUserEmail();

        if(userProfilePicValue != null) {
            byte[] decodedLogo = Base64.decode(userProfilePicValue, Base64.DEFAULT);
            editProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
        }

        userFirstName.setText(userFirstNameValue);
        userLastName.setText(userLastNameValue);
        userPhoneNumber.setText(userPhoneNumberValue);
        userEmail.setText(userEmailValue);
    }

    // UI - SIGN OUT
    public void signOutUser() {
        signOutText.setVisibility(View.GONE);
//        signOutProgressBar.setVisibility(View.VISIBLE);

        Intent i = new Intent(mContext, MainActivity.class);
        new UserProfile(mContext).clearSharedPreference(mContext);
        finishAffinity();
        startActivity(i);
        finish();
    }

    // UPDATE PROFILE FIELDS
    private void updateProfile(final TextView title, final TextView fieldToEdit, final String updateField) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Edit");
        builder.setMessage(title.getText());

        final EditText input = new EditText(mContext);
        input.setText(fieldToEdit.getText());
        builder.setView(input);

        // POSITIVE BUTTON
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedText = input.getText().toString();

                customStringRequest editProfileRequest = new customStringRequest();
                HashMap<String, String> params = new HashMap<>();

                params.put("requestType", "updateUserProfile");
                params.put("updateField", updateField);
                params.put("updateValue", updatedText);
                params.put("userToken", mUserProfile.getUserToken());
                params.put("userId", mUserProfile.getUserId());

                editProfileRequest.setUrlPath("user/update.php");
                editProfileRequest.setParams(params);

                callBackImplement callBack = new callBackImplement(mContext);
                callBack.setParams(params);
                callBack.SetRequestType("updateUserInfo");
                callBack.setFieldTextView(fieldToEdit);
                editProfileRequest.startConnection(mContext, callBack, params);
            }
        });

        // NEGATIVE BUTTON
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    // REQUEST USER PERMISSION TO SELECT AN IMAGE FROM GALLERY
    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfileEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(mContext, "Let the app use your gallery", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(UserProfileEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestPermissionCode);
        } else {
            ActivityCompat.requestPermissions(UserProfileEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            cropIntent.putExtra("outputY", 800);
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 5);
            cropIntent.putExtra("aspectY", 5);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra("scaleUpIfNeeded", true);

            File f = new File(Environment.getExternalStorageDirectory(),
                    "/temporary_profile.jpg");
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
            e.printStackTrace();
        }

        //  THE IMAGE IS INSIDE THE OUTPUT STREAM - CONVERT IT TO BYTES
        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        // IMAGE IS INSIDE BYTE ARRAY
        // ENCODING BYTES INTO STRINGS
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
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
                String profilePicPath = Environment.getExternalStorageDirectory() + "/temporary_profile.jpg";
                profilePictureBitmap = BitmapFactory.decodeFile(profilePicPath);

                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setTitle("Uploading image");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                try {
                    setProfilePicture();

//                    if(canSetImage)
//                    {
//                        Toast.makeText(mContext, "CALLED", Toast.LENGTH_SHORT).show();
//                        displayUserPicture();
//                        canSetImage = false;
//                    }
//
//                    profilePictureBitmap = BitmapFactory.decodeFile(mUserProfile.getUserProfileUrl());
//                    profilePictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
//                    editProfilePicture.setImageBitmap(profilePictureBitmap);
//                    UserProfileActivity.profilePicture.setImageBitmap(profilePictureBitmap);
//                    UserProfileActivity.fullScreenProfilePicture.setImageBitmap(profilePictureBitmap);
//                    UserSignedInActivity.userProfilePicture.setImageBitmap(profilePictureBitmap);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    // UPLOAD PROFILE PICTURE TO SERVER
    private void setProfilePicture() {
        customStringRequest profilePictureRequest = new customStringRequest("user/userProfile.php");

        // VOLLEY STRING REQUEST TO SEND USER DATA TO SERVER
        byte[] profilePictureBytes = imageToString(profilePictureBitmap).getBytes();
        String profilePictureSignature = null;

        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            MD5.update(profilePictureBytes);
            BigInteger logoHash = new BigInteger(1, MD5.digest());

            profilePictureSignature = logoHash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(TAG, "setProfilePicture: NOT VALID ALGORITHM");
        }

        HashMap<String, String> Params = new HashMap<>();

        Params.put("user_action", "updateProfile");
        Params.put("user_token", mUserProfile.getUserToken());
        Params.put("user_id", mUserProfile.getUserId());
        Params.put("user_profile", imageToString(profilePictureBitmap));
        Params.put("user_profileSignature", profilePictureSignature);

        profilePictureRequest.setParams(Params);

        callBackImplement callBack = new callBackImplement(mContext);
        callBack.setParams(Params);
        callBack.SetRequestType("user_setProfilePicture");
        profilePictureRequest.startConnection(mContext, callBack, Params);
    }

    public static void displayUserPicture() {
        UserProfile sUserProfile = new UserProfile(sContext);
        sUserProfile.setPropertiesFromSharePreference(sContext);

        byte[] decodedLogo = Base64.decode(sUserProfile.getUserProfilePicture(), Base64.DEFAULT);;
        editProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
        UserProfileActivity.profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
        UserProfileActivity.fullScreenProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
        UserSignedInActivity.userProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedLogo, 0, decodedLogo.length));
    }
}

//    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while (pStatus <= 100) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        signoutProgressBar.setProgress(pStatus);
//                        progressPercentage.setText(pStatus + " %");
//                    }
//                });
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                pStatus++;
//            }
//        }
//    }).start();
