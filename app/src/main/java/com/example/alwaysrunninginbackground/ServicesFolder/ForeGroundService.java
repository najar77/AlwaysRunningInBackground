package com.example.alwaysrunninginbackground.ServicesFolder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.alwaysrunninginbackground.R;

public class ForeGroundService extends Service {

    final String CHANNEL_ID = "ForeGround Service";

    public ForeGroundService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.d("foregrounded","f-s" + Thread.currentThread());
                    try {
                        Thread.sleep(60*1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID
                    ,CHANNEL_ID
                    , NotificationManager.IMPORTANCE_DEFAULT
            );

            getSystemService(NotificationManager.class).createNotificationChannel(channel);

            Notification.Builder notification = new Notification.Builder(this,CHANNEL_ID)
                    .setContentText("Content Text")
                    .setContentTitle("Content Title")
                    .setSmallIcon(R.drawable.ic_launcher_background);

            startForeground(1001,notification.build());
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
}