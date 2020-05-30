package com.example.aralarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_table")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    private String mDate;
    @ColumnInfo(name = "time")
    private String mTime;
    @ColumnInfo(name = "isOn")
    private boolean mIsOn;

    public Alarm(@NonNull String date, String time, boolean isOn){
        this.mDate = date;
        this.mTime = time;
        this.mIsOn = isOn;
    }

    public String getmDate(){return this.mDate;}

    public String getmTime() {return mTime;}

    public boolean ismIsOn() {return mIsOn; }
}
