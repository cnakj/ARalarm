package com.example.aralarm.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.aralarm.ApplicationClass;
import com.example.aralarm.R;
import com.example.aralarm.activity.GameActivity;

public class AlarmReceiver extends BroadcastReceiver {
    private ApplicationClass applicationClass;

    private int NOTIFICATION_ID = 1234;

    @Override
    public void onReceive(Context context, Intent intent) {
        applicationClass = (ApplicationClass)context.getApplicationContext();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        applicationClass.ringtone.play();

        Intent notificationIntent = new Intent(context, GameActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, applicationClass.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle("알람")
                .setFullScreenIntent(notificationPendingIntent, true)
                .setCategory(NotificationCompat.CATEGORY_ALARM);

        if(notificationManager != null)
            notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

