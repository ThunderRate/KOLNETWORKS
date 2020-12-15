package com.kolnetworks.koln;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class AppContext extends Application {
    private static final String SP_NAME = "kol";
    public static SharedPreferences sharedPreferences;
    private static AppContext sContext = null;
    private String TAG = this.getClass().getSimpleName();

    public static AppContext context() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
        initSharedPreferences();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    //初始化SharedPreferences
    private void initSharedPreferences() {
        sharedPreferences = context().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
