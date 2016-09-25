package com.naxesa.bodyheat.NewsFeed;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.naxesa.bodyheat.R;

import java.util.ArrayList;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class NewsFeedActivity extends Activity {

    // RecyclerView
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewNewsFeedAdapter adapter;

    // Database
    private SQLiteDatabase db;
    private NewsFeedDatabaseOpenHelper helper;

    // ArrayList
    private ArrayList<String> dates, contents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        recycleView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        helper = new NewsFeedDatabaseOpenHelper(this, "news_feed.db", null, 1);
        dates = new ArrayList<String>();
        contents = new ArrayList<String>();
        select();
        adapter = new RecyclerViewNewsFeedAdapter(this, dates, contents);
        recycleView.setAdapter(adapter);
    }

    private void select(){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("news_feed", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            dates.add(0, cursor.getString(cursor.getColumnIndex("date")));
            contents.add(0, cursor.getString(cursor.getColumnIndex("content")));
        }
    }
}
