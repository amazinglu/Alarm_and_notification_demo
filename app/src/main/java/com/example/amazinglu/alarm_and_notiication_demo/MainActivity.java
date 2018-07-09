package com.example.amazinglu.alarm_and_notiication_demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "default_channel";

    private NotificationChannel notificationChannel;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * register the alarm receiver
         *
         * in this example, we cannot use context-register receiver
         * because in this way we need to register a receiver with intent filter
         * if we create a empty intent filter, we cannot catch any broadcast
         * https://stackoverflow.com/questions/8858692/trying-to-have-a-broadcast-receiver-with-no-filter
         *
         * only can use manifest-decline receiver
         * */
//        alarmReceiver = new AlarmReceiver();
//        IntentFilter filter = new IntentFilter("android.intent.action.VIEW");
//        this.registerReceiver(alarmReceiver, filter);

        if (notificationChannel == null) {
            createNotificationChannel();
        }

        String reminderStr = getIntent().getStringExtra(MainFragment.KEY_REMINDER);
        int notificationId = getIntent().getIntExtra(MainFragment.KEY_NOTIFICATION_ID, -1);

        Bundle args = new Bundle();
        args.putString(MainFragment.KEY_REMINDER, reminderStr);
        args.putInt(MainFragment.KEY_NOTIFICATION_ID, notificationId);

        if (savedInstanceState == null) {
            // begin the fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, MainFragment.newInstance(args))
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * unregister alarm receiver
         * */
//        this.unregisterReceiver(alarmReceiver);
    }

    /**
     * https://developer.android.com/training/notify-user/build-notification
     * Before you can deliver the notification on Android 8.0 and higher, you must register your
     * app's notification channel with the system by passing an instance of NotificationChannel to
     * createNotificationChannel()
     * */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            // Register the notificationChannel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
