package com.example.alwaysrunninginbackground.ServicesFolder;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class ToastService extends Service {
    private Handler handler;
    private Runnable toastRunnable;
    private boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        toastRunnable = new Runnable() {
            @Override
            public void run() {
                showToast("This is a background toast!");
                handler.postDelayed(this, 10000); // 10 seconds delay
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isServiceRunning) {
            handler.postDelayed(toastRunnable, 0); // Start the initial toast immediately
            isServiceRunning = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(toastRunnable);
        isServiceRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
