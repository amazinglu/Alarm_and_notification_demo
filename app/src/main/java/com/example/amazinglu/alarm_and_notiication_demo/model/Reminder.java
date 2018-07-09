package com.example.amazinglu.alarm_and_notiication_demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.amazinglu.alarm_and_notiication_demo.util.DateUtil;

import java.util.Date;

public class Reminder implements Parcelable{
    public String text;
    public Date reminderDate;

    public Reminder() {}

    protected Reminder(Parcel in) {
        text = in.readString();
        reminderDate = DateUtil.StringToDateTime(in.readString());
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(DateUtil.dateTimeToString(reminderDate));
    }
}
