package com.example.amazinglu.alarm_and_notiication_demo.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.amazinglu.alarm_and_notiication_demo.AlarmReceiver;
import com.example.amazinglu.alarm_and_notiication_demo.MainFragment;
import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;

import java.util.Calendar;

public class AlarmUtil {
    /**
     * 用pendIntent setup 一个 broadcast, 在reminderDate这个时候就broadcast出去
     * */
    public static void setAlarm(Context context, Reminder reminder) {
        Calendar c = Calendar.getInstance();
        if (reminder.reminderDate.compareTo(c.getTime()) < 0) {
            return;
        }

        /**
         * get the alarm service
         * */
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /**
         * the intent is used to link the Alarm receiver with the current context
         * also pass necessary information from the current context to receiver
         *
         * define the receiver of the alarm broadcast
         * */
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(MainFragment.KEY_REMINDER, reminder);
        /**
         * PendingIntent.getBroadcast means the pendingIntent will start a broadcast
         * */
        PendingIntent alarmIntent = PendingIntent.getBroadcast
                (context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.reminderDate.getTime(), alarmIntent);
    }
}
