package com.example.android.andelaintermediatemedmanager.NotificationHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String ScheduleTitle = bundle.getString("ScheduleTitle");
            int _id = bundle.getInt("id");
            Intent myIntent = new Intent(context, NotificationServices.class);
            myIntent.putExtra("ScheduleTitle", ScheduleTitle);
            myIntent.putExtra("id",_id);
            context.startService(myIntent);
        }
    }
}
