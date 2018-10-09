package com.fzhongfei.findzhongfei_final.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fzhongfei.findzhongfei_final.R;

public class SplashScreenActivity extends AppCompatActivity {

    // EVERY ACTIVITY SETUP
    private static final String TAG = "SplashScreenActivity";
    private Context mContext = SplashScreenActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    // LOAD SPLASH SCREEN FOR 3 SECONDS BEFORE AD
//    private int countNum() {
//        timeCount++;
//        if (timeCount == 3) {
    // NO NETWORK AFTER 3 SECONDS OF SPLASH SCREEN
//            if (!NetUtils.isConnected(mContext)) {
//                continueCount = false;
//                toNextActivity();
//                finish();
//            }

    // SERVER DIDN'T LOAD AD
//            if (!loginCheckBean.isPlayAd())
//                continueCount = false;
//                toNextActivity();
//                finish();
//            } else {
    // PLAY AD
//                ivAdvertising.setVisibility(View.VISIBLE);
//                layoutSkip.setVisibility(View.VISIBLE);
//            }
//        }
    // AD LOADED AND DISPLAYED
//        if (timeCount == initTimeCount) {
//            continueCount = false;
//            toNextActivity();
//            finish();
//        }
//        return timeCount;
//    }

    // GO TO MAIN PAGE
//    public void toNextActivity() {
//        Log.d(TAG, "toNextActivity: ******************************************************************************************************** ");
//        Intent intent;
//        String token = (String) SPUtils.get(mContext, "token", "");
//        if (TextUtils.isEmpty(token)) {
//            intent = new Intent(mContext, LoginActivity.class);
//        } else {
//            intent = new Intent(mContext, MainActivity.class);
//            MyApplication.setToken(token);
//        }
//        startActivity(intent);
//        finish();
//    }

    // AD PRESENTER
//    public void getLoginCheck() {
//        // ASKING THE SERVER TO PLAY AD
//        mAdModel.getLoginCheck()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscribe<LoginCheckBean>() {
//                    @Override
//                    protected void _onNext(LoginCheckBean loginCheckBean) {
//                        getMyView().setLoginCheckBean(loginCheckBean);
//                        if (loginCheckBean.isPlayAd()) {
//                            getAdMessage();
//                        }
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//                });
//    }

//    public void getAdMessage() {
//        mAdModel.getAdMessage()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscribe<AdMessageBean>() {
//                    @Override
//                    protected void _onNext(AdMessageBean adMessageBean) {
//                        getMyView().setAdTime(adMessageBean.getAdTime());
//                        getAdPicture(adMessageBean.getAdPictureUrl(), "123.jpg");
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//                });
//    }

//    private void getLocalPicture(String localUrl) {
//        Bitmap bitmap = BitmapFactory.decodeFile(localUrl);
//        getMyView().setAdImg(bitmap);
//    }
//
//    public void getAdPicture(final String fileUrl, final String fileName) {//获取要展示的广告图片
//        if (SPUtils.get((Context) getMyView(), "adPictureUrl", "").equals(fileUrl)) {//判断是否存在缓存
//            L.d("从本地获取图片");
//            getLocalPicture((String) SPUtils.get((Context) getMyView(),"adPictureAddress",""));
//        } else {
//            L.d("从网络中获取图片");
//            mAdModel.downLoadFile(fileUrl)
//                    .subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(new Func1<ResponseBody, Bitmap>() {
//                        @Override
//                        public Bitmap call(ResponseBody responseBody) {
//                            if (responseBody != null) {
//                                L.d("收到的responseBody不为空！");
//                            }
//                            if (writeResponseBodyToDisk(responseBody, fileName, fileUrl)) {
//                                Bitmap bitmap = BitmapFactory.decodeFile(((Context) getMyView()).getExternalFilesDir(null) + File.separator + fileName);
//                                return bitmap;
//                            }
//                            return null;
//                        }
//                    }).subscribe(new RxSubscribe<Bitmap>((Context) getMyView()) {
//                @Override
//                protected void _onNext(Bitmap bitmap) {
//                    getMyView().setAdImg(bitmap);
//                }
//
//                @Override
//                protected void _onError(String message) {
//
//                }
//
//                @Override
//                public void onCompleted() {
//
//                }
//            });
//        }
//
//    }
//
//
//    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName, String fileUrl) {//保存图片到本地
//        try {
//            // todo change the file location/name according to your needs
//
//            File futureStudioIconFile = new File(((Context) getMyView()).getExternalFilesDir(null) + File.separator + fileName);
//            L.d("文件的保存地址为：" + ((Context) getMyView()).getExternalFilesDir(null) + File.separator + fileName);
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//            try {
//                byte[] fileReader = new byte[4096];
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(futureStudioIconFile);
//                while (true) {
//                    int read = inputStream.read(fileReader);
//                    if (read == -1) {
//                        break;
//                    }
//                    outputStream.write(fileReader, 0, read);
//                    fileSizeDownloaded += read;
//
//                    L.d("file download: " + fileSizeDownloaded / fileSize * 100);
//                    L.d("file download: " + fileSizeDownloaded + " of " + fileSize);
//                }
//                outputStream.flush();
//
//                SPUtils.put((Context) getMyView(), "adPictureAddress", ((Context) getMyView()).getExternalFilesDir(null) + File.separator + fileName);//下载好广告图片后，保存好当前广告图片的地址，为判断是否已经下载好图片做准备
//                SPUtils.put((Context) getMyView(), "adPictureUrl", fileUrl);
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }

//    public Observable<LoginCheckBean> getLoginCheck() {//假装服务器要展示广告
//        return Observable.create(new Observable.OnSubscribe<LoginCheckBean>() {
//            @Override
//            public void call(Subscriber<? super LoginCheckBean> subscriber) {
//                subscriber.onNext(new LoginCheckBean(true));
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<AdMessageBean> getAdMessage() {
//        return Observable.create(new Observable.OnSubscribe<AdMessageBean>() {
//            @Override
//            public void call(Subscriber<? super AdMessageBean> subscriber) {//假装要展示 3 秒广告，且广告图为如下地址
//                subscriber.onNext(new AdMessageBean(3,"http://odjfpxwey.bkt.clouddn.com/2017-3-3-20-141110180-Screenshot_2017-02-23-23-10-26-062_com.tmall.wireless.png","http://www.baidu.com"));
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<ResponseBody> downLoadFile(String fileUrl) {
//        return retrofitService.downLoadFile(fileUrl);
//    }
}