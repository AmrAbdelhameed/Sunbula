package com.example.amr.sunbula.DBFlow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabaseNewsFeed.NAME, version = AppDatabaseNewsFeed.VERSION)
public class AppDatabaseNewsFeed {
    public static final String NAME = "NewsFeed"; // we will add the .db extension

    public static final int VERSION = 1;
}
