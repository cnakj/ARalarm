package com.example.aralarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_table", primaryKeys = {"year", "month", "day", "hour", "minute"})
public class Alarm {

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

    public Alarm(@NonNull String mYear, @NonNull String mMonth, @NonNull String mDay, @NonNull String mHour, @NonNull String mMinute, boolean mOn) {
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mOn = mOn;
    }

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

}
