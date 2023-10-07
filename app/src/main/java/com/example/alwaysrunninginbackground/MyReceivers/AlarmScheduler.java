package com.example.alwaysrunninginbackground.MyReceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.example.alwaysrunninginbackground.ServicesFolder.AlarmReceiverService;

public class AlarmScheduler extends BroadcastReceiver {

    private static final int ALARM_INTERVAL_SECONDS = 5;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        scheduleNextAlarm(context);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void scheduleNextAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiverService.class);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        //long milliSec = System.currentTimeMillis() + ALARM_INTERVAL_SECONDS * 1000;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
            if (alarmManager != null) {
                long nextAlarmTime = SystemClock.elapsedRealtime() + (ALARM_INTERVAL_SECONDS * 1000); // 10 seconds
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextAlarmTime, pendingIntent);
            }
            Log.e("backgroundtasksetter", "alarm set");
        }
    }
}