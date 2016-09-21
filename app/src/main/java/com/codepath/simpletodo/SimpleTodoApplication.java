package com.codepath.simpletodo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by steve on 9/20/16.
 */

public class SimpleTodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
