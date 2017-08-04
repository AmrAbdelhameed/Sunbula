package com.example.amr.sunbula.DBFlow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabaseAllCauses.NAME, version = AppDatabaseAllCauses.VERSION)
public class AppDatabaseAllCauses {
    public static final String NAME = "AllCausesProfile"; // we will add the .db extension

    public static final int VERSION = 1;
}
