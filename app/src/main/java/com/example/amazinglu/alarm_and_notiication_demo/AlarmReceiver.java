package com.example.amazinglu.alarm_and_notiication_demo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;
import com.example.amazinglu.alarm_and_notiication_demo.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final int notificationId = 100;
        String reminderStr = intent.getStringExtra(MainFragment.KEY_REMINDER);
        Reminder reminder = ModelUtil.toObject(reminderStr, new TypeToken<Reminder>(){});

        /**
         * build the notification
         * icon is required !!!
         * */
        Intent resultIntent = new Intent(context, MainActivity.class);
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.putExtra(MainFragment.KEY_REMINDER, reminderStr);
        resultIntent.putExtra(MainFragment.KEY_NOTIFICATION_ID, notificationId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_calendar_today_black_18dp)
                .setContentTitle(context.getResources().getString(R.string.notification_title))
                .setContentText(reminder.text)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}
