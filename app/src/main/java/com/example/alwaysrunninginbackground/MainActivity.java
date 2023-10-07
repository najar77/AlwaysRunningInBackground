package com.example.alwaysrunninginbackground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.alwaysrunninginbackground.MyReceivers.AlarmScheduler;
import com.example.alwaysrunninginbackground.ServicesFolder.ToastService;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btn,workBtn;
    ToggleButton tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        tb = findViewById(R.id.toggle);
        workBtn = findViewById(R.id.btn2);

        workBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED).build();
                OneTimeWorkRequest periodicWorkRequest =new OneTimeWorkRequest.Builder(
                        MyWorker.class
                ).setConstraints(constraints)
                        .build();

                WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =  new Intent(getApplicationContext(), ToastService.class);
//                startService(intent);
                Thread currentThread = Thread.currentThread();
                String threadName = currentThread.getName();
                Toast.makeText(getApplicationContext()
                        , "This is a background toast! " + threadName
                        , Toast.LENGTH_SHORT)
                        .show();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Thread currentThread = Thread.currentThread();
                                    String threadName1 = currentThread.getName();
                                    Toast.makeText(getApplicationContext()
                                                    , "background toast in handler " + threadName1
                                                    , Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

                AlarmScheduler.scheduleNextAlarm(getApplicationContext());
            }
        });
    }
}