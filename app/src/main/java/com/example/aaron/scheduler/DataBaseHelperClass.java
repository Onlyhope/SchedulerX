package com.example.aaron.scheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aaron on 1/17/2016.
 */
public class DataBaseHelperClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuestLog.db";
    public static final String TABLE_NAME = "quest_log";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Deadline";
    public static final String COL_4 = "Type";


    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT, DEADLINE TEXT, TYPE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
