package com.example.aralarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_table", primaryKeys = {"date", "time"})
public class Alarm {

    @ColumnInfo(name = "date")
    @NonNull
    private String mDate;
    @NonNull
    @ColumnInfo(name = "time")
    private String mTime;
    @ColumnInfo(name = "isOn")
    private boolean mOn;

    public Alarm(@NonNull String date, @NonNull  String time, boolean on){
        this.mDate = date;
        this.mTime = time;
        this.mOn = on;
    }

    public String getMDate() {return this.mDate;}

    public String getMTime() {return mTime;}

    public boolean isMOn() {return mOn; }

    public int getHour() {return Integer.parseInt(this.mTime.substring(0,2));}

    public int getMinute() {return Integer.parseInt(this.mTime.substring(2,4));}

    public int getYear() {return Integer.parseInt(this.mDate.substring(0,4));}

    public int getMonth() {return Integer.parseInt(this.mDate.substring(4,6));}

    public int getDay() {return Integer.parseInt(this.mDate.substring(6,8));}
}
