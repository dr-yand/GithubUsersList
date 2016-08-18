package com.kritsin.githubuserslist.data.net;

import com.kritsin.githubuserslist.util.AppConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final OkHttpClient mHttpClient;

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(logging);

        mHttpClient = httpClientBuilder
                .connectTimeout(AppConfig.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(AppConfig.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(AppConfig.getTimeout(), TimeUnit.SECONDS)
                .build();
    }


    public static ApiInterface getApiInterface() {
        return getRetrofit().create(ApiInterface.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.getApiEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();
    }
}
