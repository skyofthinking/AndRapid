package com.joyue.tech.gankio.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author JiangYH
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "GankIO"; // we will add the .db extension

    public static final int VERSION = 1;
}