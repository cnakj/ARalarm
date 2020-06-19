package com.example.aralarm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class AlarmRepository {

    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllAlarm;

    AlarmRepository(Application application){
        AlarmRoomDatabase db = AlarmRoomDatabase.getDatabase(application);
        mAlarmDao = db.alarmDao();
        mAllAlarm = mAlarmDao.getOrderedWords();
    }

    LiveData<List<Alarm>> getAllAlarm() {return mAllAlarm;}

    void insert(Alarm alarm){
        AlarmRoomDatabase.databaseWriteExecutor.execute(() ->{
            mAlarmDao.insert(alarm);
        });
    }

    void update(Alarm alarm){
        AlarmRoomDatabase.databaseWriteExecutor.execute(() ->{
            mAlarmDao.update(alarm);
        });
    }
}
