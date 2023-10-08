package com.example.alwaysrunninginbackground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.alwaysrunninginbackground.MyReceivers.AlarmScheduler;
import com.example.alwaysrunninginbackground.ServicesFolder.ForeGroundService;

public class MainActivity extends AppCompatActivity {

    Button btn, workBtnOnce,foreGround,workBtnPeriodic;
    EditText onceText, periodicText;
    ToggleButton tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        tb = findViewById(R.id.toggle);
        workBtnOnce = findViewById(R.id.worker_once);
        workBtnPeriodic = findViewById(R.id.worker_periodic);
        foreGround = findViewById(R.id.foreground);
        onceText = findViewById(R.id.once_time);
        periodicText = findViewById(R.id.periodic_time);

        workBtnOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED).build();
                OneTimeWorkRequest onceWorkRequest =new OneTimeWorkRequest.Builder(
                        MyWorker.class
                ).setConstraints(constraints)
                        .build();
                WorkManager.getInstance(getApplicationContext()).enqueue(onceWorkRequest);
            }
        });

        foreGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForeGroundService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                }
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