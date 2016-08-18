package com.kritsin.githubuserslist.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AppConfig {
    private static SharedPreferences sPrefs;

    private static final String API_ENDPOINT = "API_URL";

    private static final String END_POINT = "https://api.github.com/";

    private static final int CONNECT_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;


    public static void init(Context context) {
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getApiEndpoint() {
        return sPrefs.getString(API_ENDPOINT, END_POINT);
    }

    public static void setApiEndpoint(String name) {
        sPrefs.edit().putString(API_ENDPOINT, name).apply();
    }

    public static int getConnectTimeout(){
        return CONNECT_TIMEOUT;
    }

    public static int getWriteTimeout(){
        return WRITE_TIMEOUT;
    }

    public static int getTimeout(){
        return TIMEOUT;
    }

}
