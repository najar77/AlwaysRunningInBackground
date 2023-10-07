package com.example.alwaysrunninginbackground.ServicesFolder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import android.app.PendingIntent;
import android.os.SystemClock;

public class AlarmReceiverService extends IntentService {
    private static final int ALARM_INTERVAL_SECONDS = 5;
    public AlarmReceiverService() {
        super("AlarmReceiverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // This method will be called when the AlarmManager triggers the service.
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        showToast("This is a background toast! " + threadName);

        // Schedule the next alarm
        scheduleNextAlarm();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void scheduleNextAlarm() {
        Intent intent = new Intent(this, AlarmReceiverService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
            if (alarmManager != null) {
                long nextAlarmTime = SystemClock.elapsedRealtime() + (ALARM_INTERVAL_SECONDS * 1000); // 10 seconds
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextAlarmTime, pendingIntent);
            }
            Log.e("backgroundtasksetter", "alarm set");
        }
    }
}

