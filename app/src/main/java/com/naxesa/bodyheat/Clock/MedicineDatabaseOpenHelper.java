package com.naxesa.bodyheat.Clock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class MedicineDatabaseOpenHelper extends SQLiteOpenHelper{

    private final String clockTable = "clock";

    public MedicineDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String clockSql = "create table " + clockTable + " (" +
                "_id integer primary key autoincrement, " +
                "name text" + ");";
        db.execSQL(clockSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String clockSql = "drop table if exists " + clockTable;
        db.execSQL(clockSql);
        onCreate(db);
    }
}
