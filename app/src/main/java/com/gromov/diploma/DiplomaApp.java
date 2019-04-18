package com.gromov.diploma;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class DiplomaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}