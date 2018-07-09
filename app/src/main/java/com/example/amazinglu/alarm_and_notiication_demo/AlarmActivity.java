package com.example.amazinglu.alarm_and_notiication_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;
import com.example.amazinglu.alarm_and_notiication_demo.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.alarm_text) TextView alarmText;

    private Reminder reminder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String reminderStr = getIntent().getStringExtra(MainFragment.KEY_REMINDER);
        reminder = ModelUtil.toObject(reminderStr, new TypeToken<Reminder>(){});

        alarmText.setText(reminder.text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
