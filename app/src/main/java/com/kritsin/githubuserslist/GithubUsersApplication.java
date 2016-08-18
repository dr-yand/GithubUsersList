package com.kritsin.githubuserslist;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.kritsin.githubuserslist.util.AppConfig;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;


public class GithubUsersApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

		AppConfig.init(this);

        new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(this, Integer.MAX_VALUE))
                .build();

    }

}
