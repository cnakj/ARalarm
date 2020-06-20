package com.example.aralarm.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {

    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllAlarm;

    public AlarmRepository(Application application){
        AlarmRoomDatabase db = AlarmRoomDatabase.getDatabase(application);
        mAlarmDao = db.alarmDao();
        mAllAlarm = mAlarmDao.getOrderedWords();
    }

    public LiveData<List<Alarm>> getAllAlarm() {return mAllAlarm;}

    public LiveData<Integer> getOnNum(){ return mAlarmDao.getOnAlarms();}

    public void insert(Alarm alarm){
        AlarmRoomDatabase.databaseWriteExecutor.execute(() ->{
            mAlarmDao.insert(alarm);
        });
    }

    public void update(Alarm alarm){
        AlarmRoomDatabase.databaseWriteExecutor.execute(() ->{
            mAlarmDao.update(alarm);
        });
    }

    public void delete(Alarm alarm){
        AlarmRoomDatabase.databaseWriteExecutor.execute(() ->{
            mAlarmDao.delete(alarm);
        });
    }
}
