package com.naxesa.bodyheat.Clock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class MedicineDatabaseOpenHelper extends SQLiteOpenHelper{

    private final String clockTable = "clock";
    private final String medicineTable = "medicine";

    public MedicineDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String clockSql = "create table " + clockTable + " (" +
                "_id integer primary key autoincrement, " +
                "name text, " +
                "date integer);";
        String medicineSql = "create table " + medicineTable + " (" +
                "key integer, " +
                "hour integer, " +
                "minute integer);";
        db.execSQL(clockSql);
        db.execSQL(medicineSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String clockSql = "drop table if exists " + clockTable;
        String medicineSql = "drop table if exists " + medicineTable;
        db.execSQL(clockSql);
        db.execSQL(medicineSql);
        onCreate(db);
    }
}
