package com.example.aaron.scheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aaron on 1/17/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuestLog.db";
    public static final String TABLE_NAME = "quest_log";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Deadline";
    public static final String COL_4 = "Type";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY," +
                "NAME TEXT, DEADLINE TEXT, TYPE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String insertData(String name, String deadline, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, deadline);
        contentValues.put(COL_4, type);

        long result = db.insert(TABLE_NAME, null, contentValues);

        Cursor id = db.rawQuery("select * from " + TABLE_NAME + " where name = " + name, null);

        if (id.getCount() == 0) {
            return "ERROR";
        }

        StringBuffer buffer = new StringBuffer();
        while (id.moveToNext()) {
            buffer.append(id.getString(0));
        }
        return buffer.toString();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);

        return result;
    }

    public boolean updateData(String id, String name, String deadline, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, deadline);
        contentValues.put(COL_4, type);

        int rowsAffected = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        if (rowsAffected > 0)
            return true;
        else
            return false;
    }

    public Integer deleteData(Quest deleteQuest) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = deleteQuest.getId();

//        String name = deleteQuest.getQuestName();
//        return db.delete(TABLE_NAME, "name = ?", new String[]{name});

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
