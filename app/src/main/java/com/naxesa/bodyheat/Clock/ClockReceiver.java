package com.naxesa.bodyheat.Clock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.naxesa.bodyheat.NewsFeed.NewsFeedDatabaseOpenHelper;
import com.naxesa.bodyheat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class ClockReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private Notification.Builder builder;

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals("com.naxesa.bodyheat")) {
            String name = intent.getStringExtra("name");
            int date = intent.getIntExtra("date", 1);
            int requestCode = intent.getIntExtra("requestcode", 0);
            String time = intent.getStringExtra("time");
            date = CheckAlarmIsDelete(name, context, time, date);
            if (date >= 1) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new Notification.Builder(context);
                builder.setSmallIcon(R.drawable.ic_healing_white_24dp);
                builder.setTicker("약 먹을 시간");
                builder.setWhen(System.currentTimeMillis());
                builder.setContentTitle("약 먹을 시간");
                builder.setContentText(name + " 먹을 시간입니다.");
                builder.setDefaults(Notification.DEFAULT_VIBRATE);
                builder.setAutoCancel(true);

                builder.setPriority(Notification.PRIORITY_MAX);
                notificationManager.notify(111, builder.build());
                if(date==1){
                    CheckAlarmIsExist(name, context, time);
                }else{
                    setAlarm(context,name,date,requestCode, time);
                }
                addClock(context, name);
            } else{
                CheckAlarmIsExist(name, context, time);
            }

        }
    }

    private void CheckAlarmIsExist(String name, Context context, String time) {
        helper = new MedicineDatabaseOpenHelper(context, "clock.db", null, 2);
        db = helper.getWritableDatabase();
        db.delete("medicine", "date=?", new String[]{time});
        db = helper.getReadableDatabase();
        Cursor c = db.query("medicine", null, null, null, null, null, null);
        while(c.moveToNext()){
            if(name.equals(c.getString(c.getColumnIndex("name")))){
                return;
            }
        }
        db.delete("clock", "name=?", new String[]{name});
    }

    private int CheckAlarmIsDelete(String name, Context context, String time, int date){
        helper = new MedicineDatabaseOpenHelper(context, "clock.db", null, 2);
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from clock where name='"+name+"'",null);
        if(c.getCount()>0){
                return date;
        }
        return 0;
    }

    private void addClock(Context context, String name) {
        helper = new NewsFeedDatabaseOpenHelper(context, "news_feed.db", null, 1);
        db = helper.getWritableDatabase();

        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = transFormat.format(from);

        String content = "약 알람 : " + name;

        ContentValues values = new ContentValues();
        values.put("state", "clock");
        values.put("date", date);
        values.put("content", content);
        db.insert("news_feed", null, values);
    }

    private void setAlarm(Context context, String name, int date, int requestCode, String time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000l*60*60*24, pendingIntent(context,name, date, requestCode, time));
    }

    private PendingIntent pendingIntent(Context context, String name, int date, int requestCode, String time) {
        date -=1;
        Intent intent = new Intent("com.naxesa.bodyheat");
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("requestcode", requestCode+1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode+1, intent, 0);
        return pendingIntent;
    }
}
