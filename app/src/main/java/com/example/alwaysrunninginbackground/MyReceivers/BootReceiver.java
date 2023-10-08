package com.example.alwaysrunninginbackground.MyReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.alwaysrunninginbackground.ServicesFolder.ForeGroundService;

import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("foregrounded","got");
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent startForeground = new Intent(context, ForeGroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("foregrounded","started");
                context.startForegroundService(startForeground);
            }
        }
    }
}