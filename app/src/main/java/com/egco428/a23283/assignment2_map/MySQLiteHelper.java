package com.egco428.a23283.assignment2_map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LATITUDE = "one";
    public static final String COLUMN_LONGTITUDE = "two";


    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = " create table " //create table comments (_id interger primary key autoincrement, comment text not null);
             + TABLE_COMMENTS + " (" +
                 COLUMN_ID + " integer primary key autoincrement, " +
             COLUMN_USERNAME + " text not null, " +
             COLUMN_PASSWORD +" text not null, " +
             COLUMN_LATITUDE +" text not null, " +
             COLUMN_LONGTITUDE + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
