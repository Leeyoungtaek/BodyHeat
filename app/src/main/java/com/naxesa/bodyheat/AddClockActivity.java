package com.naxesa.bodyheat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class AddClockActivity extends Activity implements TimePicker.OnTimeChangedListener {

    private final String TAG = "AddClockActivity";

    // Views
    private EditText inputName, inputDate;
    private TimePicker timePicker;

    // AlarmManager
    private AlarmManager alarmManager;

    // Date Option
    private GregorianCalendar gregorianCalendar;

    // Notification
    private NotificationManager notificationManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);

        // View Reference
        inputName = (EditText) findViewById(R.id.name);
        inputDate = (EditText) findViewById(R.id.date);
        timePicker = (TimePicker) findViewById(R.id.time_picker);

        // Get System Service
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // current time
        gregorianCalendar = new GregorianCalendar();
        Log.d(TAG, gregorianCalendar.getTime().toString());

        // View Setting
        timePicker.setCurrentHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(gregorianCalendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(this);
    }

    private void setAlarm() {
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pendingIntent());
    }

    private PendingIntent pendingIntent() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        return pendingIntent;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), i, i1);
        Log.d(TAG, gregorianCalendar.getTime().toString());
    }
}
