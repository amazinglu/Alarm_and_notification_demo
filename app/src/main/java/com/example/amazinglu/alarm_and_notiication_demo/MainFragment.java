package com.example.amazinglu.alarm_and_notiication_demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.amazinglu.alarm_and_notiication_demo.model.Reminder;
import com.example.amazinglu.alarm_and_notiication_demo.util.AlarmUtil;
import com.example.amazinglu.alarm_and_notiication_demo.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    public static final String KEY_REMINDER = "reminder";
    public static final String KEY_NOTIFICATION_ID = "notification_id";

    @BindView(R.id.notification_text) TextView notificationTextView;
    @BindView(R.id.notification_text_input) EditText notificationInputView;
    @BindView(R.id.set_date) TextView setDate;
    @BindView(R.id.set_time) TextView setTime;
    @BindView(R.id.set_reminder_confirm) Button confirm;

    private Reminder reminder;

    public static MainFragment newInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        int notificationId = getArguments().getInt(KEY_NOTIFICATION_ID);
        reminder = getArguments().getParcelable(KEY_REMINDER);

        // set up the text notifiew
        if (reminder.text != null) {
            notificationInputView.setVisibility(View.VISIBLE);
            notificationTextView.setText(reminder.text);
        } else {
            notificationTextView.setVisibility(View.GONE);
        }

        // cancel the notification
        if (notificationId != -1) {
            ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                    .cancel(notificationId);
        }

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = getCalendarFromRemindDate();
                MOndateSetListener mOndateSetListener = new MOndateSetListener();
                Dialog dialog = new DatePickerDialog(getContext(), mOndateSetListener,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = getCalendarFromRemindDate();
                MOnTimeSetListener mOnTimeSetListener = new MOnTimeSetListener();
                Dialog dialog = new TimePickerDialog(getContext(), mOnTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append(notificationInputView.getText().toString());
                sb.append('\n');
                sb.append(setDate.getText().toString());
                sb.append('\n');
                sb.append(setTime.getText().toString());
                notificationTextView.setVisibility(View.VISIBLE);
                notificationTextView.setText(sb.toString());

                reminder.text = notificationInputView.getText().toString();
                // set the alarm
                AlarmUtil.setAlarm(getContext(), reminder);
            }
        });
    }

    private Calendar getCalendarFromRemindDate() {
        Calendar c = Calendar.getInstance();
        if (reminder.reminderDate != null) {
            c.setTime(reminder.reminderDate);
        }
        return c;
    }

    class MOndateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = getCalendarFromRemindDate();
            c.set(year, month, dayOfMonth);
            reminder.reminderDate = c.getTime();
            setDate.setText(DateUtil.dateToString(reminder.reminderDate));
        }
    }

    class MOnTimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar c = getCalendarFromRemindDate();
            /**
             * 一定要用 HOUR_OF_DAY，这样子才能改变date中的hour和minute
             * */
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            reminder.reminderDate = c.getTime();
            setTime.setText(DateUtil.timeToString(reminder.reminderDate));
        }
    }
}
