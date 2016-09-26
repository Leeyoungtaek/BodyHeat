package com.naxesa.bodyheat.Clock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naxesa.bodyheat.Clock.Listener.DeleteMedicineEventListener;
import com.naxesa.bodyheat.Clock.Listener.TimeDialogEventListener;
import com.naxesa.bodyheat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class AddClockActivity extends Activity {

    private final String TAG = "AddClockActivity";

    // Views
    private EditText inputName, inputDate;
    private Button btnAddMedicine, btnSet;
    private TimeDialog timeDialog;

    // RecyclerView
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewMedicineAdapter adapter;

    // Database
    private SQLiteDatabase db;
    private MedicineDatabaseOpenHelper helper;

    // Date Option
    private GregorianCalendar gregorianCalendar;

    // AlarmManager
    private AlarmManager alarmManager;

    // Data
    private ArrayList<GregorianCalendar> gregorianCalendars;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);

        // View Reference
        inputName = (EditText) findViewById(R.id.name);
        inputDate = (EditText) findViewById(R.id.date);
        btnAddMedicine = (Button) findViewById(R.id.add_medicine);
        btnSet = (Button) findViewById(R.id.set);

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(AddClockActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Open Database
        helper = new MedicineDatabaseOpenHelper(AddClockActivity.this, "clock.db", null, 2);

        // View Event
        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog = new TimeDialog(AddClockActivity.this, new TimeDialogEventListener() {
                    @Override
                    public void TimeDialogEvent(GregorianCalendar gregorianCalendar) {
                        setGregorianCalendar(gregorianCalendar);
                        gregorianCalendars.add(gregorianCalendar);
                        adapter = new RecyclerViewMedicineAdapter(AddClockActivity.this, gregorianCalendars, new DeleteMedicineEventListener() {
                            @Override
                            public void DeleteMedicineEvent(int position) {
                                gregorianCalendars.remove(position);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });
                timeDialog.show();
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int date = 0;
                String name = inputName.getText().toString().trim();
                String dateString = inputDate.getText().toString();
                if (!TextUtils.isEmpty(dateString)) {
                    date = Integer.parseInt(dateString);
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddClockActivity.this, "약 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(dateString)) {
                    Toast.makeText(AddClockActivity.this, "약이 몇일분인지 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (date <= 0) {
                    Toast.makeText(AddClockActivity.this, "올바른 데이터를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (gregorianCalendars.size() <= 0) {
                    Toast.makeText(AddClockActivity.this, "시간을 설정해주세요", Toast.LENGTH_SHORT).show();
                }
                insertDatabase(name);
                setAlarms(name, date);
                setResult(RESULT_OK);
                finish();
            }
        });

        // Get System Service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // new Array
        gregorianCalendars = new ArrayList<GregorianCalendar>();
    }

    private void insertDatabase(String name) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        db.insert("clock", null, values);
        for(int i = 0; i<gregorianCalendars.size(); i++){
            values = new ContentValues();
            values.put("name", name);
            values.put("date", String.valueOf(gregorianCalendars.get(i).getTimeInMillis()));
            db.insert("medicine", null, values);
        }
    }

    public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
        this.gregorianCalendar = gregorianCalendar;
    }

    private void setAlarm(String name, int date, int requestCode, String time, GregorianCalendar gregorianCalendar) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (gregorianCalendar.getTimeInMillis() < System.currentTimeMillis()) {
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pendingIntent(name, date, requestCode, time));
    }

    private void setAlarms(String name, int date){
        for (int i = 0; i<gregorianCalendars.size(); i++){
            int requestCode = (int) (System.currentTimeMillis()%10000 + i*10000);
            setAlarm(name, date, requestCode, String.valueOf(gregorianCalendars.get(i).getTimeInMillis()), gregorianCalendars.get(i));
        }
    }

    private PendingIntent pendingIntent(String name, int date, int requestCode, String time) {
        Intent intent = new Intent("com.naxesa.bodyheat");
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("requestcode", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
        return pendingIntent;
    }
}
