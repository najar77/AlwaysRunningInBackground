package com.example.alwaysrunninginbackground;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Thread currentThread = Thread.currentThread();
                String threadName = currentThread.getName();
                Toast.makeText(getApplicationContext()
                                , "background toast! from worker" + threadName
                                , Toast.LENGTH_SHORT)
                        .show();
            }
        });





        return Result.success();
    }
}
