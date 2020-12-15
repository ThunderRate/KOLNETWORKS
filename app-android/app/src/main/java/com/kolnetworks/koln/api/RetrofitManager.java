package com.kolnetworks.koln.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    // 以Singleton模式建立
    private static RetrofitManager mInstance = new RetrofitManager();

    private ApiServiceInterface myAPIService;

    private RetrofitManager() {

        // 設置baseUrl即要連的網站，addConverterFactory用Gson作為資料處理Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kolnetworks.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myAPIService = retrofit.create(ApiServiceInterface.class);
    }

    public static RetrofitManager getInstance() {
        return mInstance;
    }

    public ApiServiceInterface getAPI() {
        return myAPIService;
    }
}
