package com.example.amazinglu.alarm_and_notiication_demo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "default_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        final int notificationId = 100;
        Reminder reminder = intent.getParcelableExtra(MainFragment.KEY_REMINDER);

        /**
         * build the notification
         * icon is required !!!
         * */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(reminder.text);

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(MainFragment.KEY_REMINDER, reminder);
        resultIntent.putExtra(MainFragment.KEY_NOTIFICATION_ID, notificationId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(notificationId, builder.build());
    }
}
