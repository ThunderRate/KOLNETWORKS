package com.kolnetworks.koln.api;

import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;

import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kolnetworks.koln.AppContext;
import com.kolnetworks.koln.BuildConfig;
import com.kolnetworks.koln.data.SPApi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIServiceClient {
    private static final String API_URL = "https://api.kolnetworks.com";
    private static ApiServiceInterface apiClient;

    private APIServiceClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                message -> {
                    if (BuildConfig.DEBUG) {
                        Log.v("LinNet", message);
                    }
                }
        );
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        HttpLoggingInterceptor httpLoggingInterceptor2 = new HttpLoggingInterceptor(
                message -> {
                    if (BuildConfig.DEBUG) {
                        Log.v("LinNet", message);
                    }
                }
        );
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(httpLoggingInterceptor2)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("Authorization" , "Bearer " + SPApi.getToken())
                            .addHeader("Content-Type" , "application/json")
                            .addHeader("Accept" , "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        apiClient = retrofit.create(ApiServiceInterface.class);
    }

    public static ApiServiceInterface getInstance() {
        if (apiClient == null) {
            synchronized (APIServiceClient.class) {
                if (apiClient == null) {
                    new APIServiceClient();
                }
            }
        }
        return apiClient;
    }

    public static class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> apply(final Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap((Function<Throwable, Observable<?>>) throwable -> {
                        if (++retryCount < maxRetries) {
                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelayMillis,
                                    TimeUnit.MILLISECONDS);
                        }

                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    });
        }
    }
}
