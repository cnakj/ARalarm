package com.example.aralarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "alarm_table",
        indices = {@Index(value = {"year", "month", "day", "hour", "minute"}, unique = true)})
public class Alarm implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "year")
    @NonNull
    private String mYear;
    @ColumnInfo(name = "month")
    @NonNull
    private String mMonth;
    @ColumnInfo(name = "day")
    @NonNull
    private String mDay;
    @ColumnInfo(name = "hour")
    @NonNull
    private String mHour;
    @ColumnInfo(name = "minute")
    @NonNull
    private String mMinute;
    @ColumnInfo(name = "isOn")
    private boolean mOn;

    public Alarm(String id, @NonNull String mYear, @NonNull String mMonth, @NonNull String mDay, @NonNull String mHour, @NonNull String mMinute, boolean mOn) {
        this.id = id;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mOn = mOn;
    }

    public Alarm(String mId, @NonNull int mYear, @NonNull int mMonth, @NonNull int mDay, @NonNull int mHour, @NonNull int mMinute, boolean mOn) {
        this.id = mId;
        this.mYear = Integer.toString(mYear);
        this.mMonth = Integer.toString(mMonth);
        this.mDay = Integer.toString(mDay);
        this.mHour = Integer.toString(mHour);
        this.mMinute = Integer.toString(mMinute);
        this.mOn = mOn;
    }

    public String getId() { return id; }

    @NonNull
    public String getYear() { return mYear; }

    @NonNull
    public String getMonth() { return mMonth; }

    @NonNull
    public String getDay() { return mDay; }

    @NonNull
    public String getHour() { return mHour; }

    @NonNull
    public String getMinute() { return mMinute; }

    public boolean isOn() { return mOn; }

    public void toggle(boolean mOn) { this.mOn = mOn; }
}
