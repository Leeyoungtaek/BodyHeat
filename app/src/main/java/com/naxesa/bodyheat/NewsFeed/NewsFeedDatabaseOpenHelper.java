package com.naxesa.bodyheat.NewsFeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class NewsFeedDatabaseOpenHelper extends SQLiteOpenHelper{

    private final String Table = "news_feed";

    public NewsFeedDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Table + " (" +
                "_id integer primary key autoincrement, " +
                "state text, " +
                "date text, " +
                "content text" + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String clockSql = "drop table if exists " + Table;
        db.execSQL(clockSql);
        onCreate(db);
    }
}
