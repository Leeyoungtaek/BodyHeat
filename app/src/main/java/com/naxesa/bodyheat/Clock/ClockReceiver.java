package com.naxesa.bodyheat.Clock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.naxesa.bodyheat.R;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class ClockReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private Notification.Builder builder;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals("com.naxesa.bodyheat")){
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.ic_healing_white_24dp);
            builder.setTicker("약 먹을 시간");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("약 먹을 시간");
            builder.setContentText("약 먹을 시간입니다.");
            builder.setDefaults(Notification.DEFAULT_VIBRATE);
            builder.setAutoCancel(true);

            builder.setPriority(Notification.PRIORITY_MAX);
            notificationManager.notify(111, builder.build());
        }
    }
}
