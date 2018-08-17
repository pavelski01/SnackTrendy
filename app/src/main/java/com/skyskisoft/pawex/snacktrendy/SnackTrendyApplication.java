package com.skyskisoft.pawex.snacktrendy;

import android.app.Application;
import android.content.Context;

public class SnackTrendyApplication extends Application {

    public static SnackTrendyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static SnackTrendyApplication getInstance() {
        return instance;
    }
}