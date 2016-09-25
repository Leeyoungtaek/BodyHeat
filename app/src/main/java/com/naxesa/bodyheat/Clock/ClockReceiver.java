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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.naxesa.bodyheat.NewsFeed.NewsFeedDatabaseOpenHelper;
import com.naxesa.bodyheat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            int date = intent.getIntExtra("date", 1) - 1;
            int requestCode = intent.getIntExtra("requestcode", 0);
            intent.removeExtra("data");
            intent.putExtra("date", date);
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
            if (date == 0) {
                Log.d("WWW", "onReceive: ");
//                releaseAlarm(context, name, date + 1, requestCode);
                CheckAlarmIsExist(name, context);
            }
            addClock(context, name);
        }
    }

    private void releaseAlarm(Context context, String name, int date, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = pendingIntent(context, name, date, requestCode);
        alarmManager.cancel(pendingIntent);
        ClockData.pendingIntents.remove(ClockData.pendingIntents.indexOf(pendingIntent));
        ClockData.intentNames.remove(ClockData.pendingIntents.indexOf(pendingIntent));
    }

    private PendingIntent pendingIntent(Context context, String name, int date, int requestCode) {
        Intent intent = new Intent("com.naxesa.bodyheat");
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("requestcode", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        return pendingIntent;
    }

    private void CheckAlarmIsExist(String name, Context context) {
        for (int i = 0; i < ClockData.intentNames.size(); i++) {
            if (ClockData.intentNames.get(i).equals(name)) {
                return;
            }
        }
        helper = new MedicineDatabaseOpenHelper(context, "clock.db", null, 2);
        db = helper.getWritableDatabase();
        db.delete("clock", "name=?", new String[]{name});
    }

    private void addClock(Context context, String name) {
        helper = new NewsFeedDatabaseOpenHelper(context, "news_feed.db", null, 1);
        db = helper.getWritableDatabase();

        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = transFormat.format(from);

        String content = "약 알람 : " + name;

        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("content", content);
        db.insert("news_feed", null, values);
    }
}
