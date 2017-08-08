package com.example.amr.sunbula.DBFlow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabaseSunbula.NAME, version = AppDatabaseSunbula.VERSION)
public class AppDatabaseSunbula {
    public static final String NAME = "Sunbula"; // we will add the .db extension

    public static final int VERSION = 1;
}
