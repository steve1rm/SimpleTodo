package com.codepath.simpletodo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by steve on 9/20/16.
 */

public class SimpleTodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /* Configure logging */
        Timber.plant(new Timber.DebugTree());

        /* Initialize realm instead of passing the context in Builder(this) see below */
        Realm.init(SimpleTodoApplication.this);

        /* Configure realm database */
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder()
                .name("simpletodo.realm")
                .build();

        /*Use only when you want to clean the database and start again*/

        /*This realm DB will be the default*/

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
