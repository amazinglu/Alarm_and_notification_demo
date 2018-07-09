package com.example.amazinglu.alarm_and_notiication_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Reminder reminder = getIntent().getParcelableExtra(MainFragment.KEY_REMINDER);
        int notificationId = getIntent().getIntExtra(MainFragment.KEY_NOTIFICATION_ID, -1);

        Bundle args = new Bundle();
        if (reminder == null) {
            reminder = new Reminder();
        }
        args.putParcelable(MainFragment.KEY_REMINDER, reminder);
        args.putInt(MainFragment.KEY_NOTIFICATION_ID, notificationId);

        if (savedInstanceState == null) {
            // begin the fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, MainFragment.newInstance(args))
                    .commit();
        }
    }
}
