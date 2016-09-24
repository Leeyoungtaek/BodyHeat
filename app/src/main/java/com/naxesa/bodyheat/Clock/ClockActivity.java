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
    private final int REQUEST_CODE = 1000;

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
        helper = new MedicineDatabaseOpenHelper(ClockActivity.this, "clock.db", null, 2);
        select();

        // RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(ClockActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewClockAdapter(ClockActivity.this, names, new DeleteClockEventListener() {
            @Override
            public void DeleteClockEvent(int position) {
                String name = names.get(position);
                delete(name);
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
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    private void select(){
        names = new ArrayList<String>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("clock", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }
    }

    private void delete(String name){
        int id= 0;
        db = helper.getReadableDatabase();
        Cursor c = db.query("clock", null, null, null, null, null, null);
        while(c.moveToNext()){
            if(name.equals(c.getString(c.getColumnIndex("name")))){
                id = c.getInt(c.getColumnIndex("_id"));
                break;
            }
        }
        db = helper.getWritableDatabase();
        db.delete("clock", "name=?", new String[]{name});
        db.delete("medicine", "key=?", new String[]{String.valueOf(id)});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode==RESULT_OK){
                    select();
                    adapter = new RecyclerViewClockAdapter(ClockActivity.this, names, new DeleteClockEventListener() {
                        @Override
                        public void DeleteClockEvent(int position) {
                            names.remove(position);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
                break;
            default:
                break;
        }
    }
}
