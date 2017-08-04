package com.example.amr.sunbula.DBFlow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabaseMyCauses.NAME, version = AppDatabaseMyCauses.VERSION)
public class AppDatabaseMyCauses {
    public static final String NAME = "MyCausesProfile"; // we will add the .db extension

    public static final int VERSION = 1;
}
