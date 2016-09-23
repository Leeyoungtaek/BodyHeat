package com.naxesa.bodyheat.Clock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naxesa.bodyheat.Clock.AddClockActivity;
import com.naxesa.bodyheat.Clock.Listener.DeleteClockEventListener;
import com.naxesa.bodyheat.R;

import java.util.ArrayList;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class ClockActivity extends Activity implements View.OnClickListener{

    // Const
    private final String TAG = "ClockActivity";

    // Views
    private Button btnAdd;

    // RecyclerView
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewClockAdapter adapter;

    // Data
    private ArrayList<String> names;

    // Database
    private SQLiteDatabase db;
    private MedicineDatabaseOpenHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        // View Reference
        btnAdd = (Button)findViewById(R.id.add);

        // View Event
        btnAdd.setOnClickListener(this);

        // Open Database and Select
        helper = new MedicineDatabaseOpenHelper(ClockActivity.this, "clock.db", null, 1);
        select();

        // RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(ClockActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewClockAdapter(ClockActivity.this, names, new DeleteClockEventListener() {
            @Override
            public void DeleteClockEvent(int position) {
                names.remove(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.add:
                intent = new Intent(getApplicationContext(), AddClockActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private void select(){
        names = new ArrayList<String>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("clock", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }
    }
}
