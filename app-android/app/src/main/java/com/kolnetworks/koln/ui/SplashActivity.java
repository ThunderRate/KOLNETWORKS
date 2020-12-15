package com.kolnetworks.koln.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.ui.login.LoginActivity;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class SplashActivity extends AppCompatActivity {

    int kolNotify = 0;
    String uuid = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        AppCenter.start(getApplication(), "3c574f60-7988-4556-bb8c-34ba6b5cb2ed",
                Analytics.class, Crashes.class);

        Log.d("JJJ", " fcm : " + getIntent().getExtras());
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                if (value == null) {
                    continue;
                }
                if (key.equals("kolNotify")){
                    kolNotify = Integer.parseInt((String) value);
                }

                if (key.equals("project_uuid")){
                    uuid = value.toString();
                }
            }
        }

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome(){
        Bundle bundle = new Bundle();
        bundle.putString("uuid", uuid);
        bundle.putInt("kolNotify",kolNotify);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        finish();
    }

}