package com.example.android.andelaintermediatemedmanager.NotificationHandler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.andelaintermediatemedmanager.MainActivity;
import com.example.android.andelaintermediatemedmanager.R;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class NotificationServices extends Service {


    private NotificationManager mManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart (Intent intent, int startId) {
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(
                        this.getApplicationContext().NOTIFICATION_SERVICE);
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String ScheduleTitle = bundle.getString("ScheduleTitle");
            int id = bundle.getInt("id");

            Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), id, intent1, PendingIntent.FLAG_ONE_SHOT);
            Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
            builder.setAutoCancel(true);
            builder.setContentTitle("Schedule Reminder");
            builder.setContentText(ScheduleTitle);
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(true);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.build();
            }
            Notification notification = builder.getNotification();
            mManager.notify(id, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
