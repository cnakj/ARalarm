package com.example.aralarm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;

    private LiveData<List<Alarm>> mAllAlarms;

    public AlarmViewModel (Application application){
        super(application);
        mRepository = new AlarmRepository(application);
        mAllAlarms = mRepository.getAllAlarm();
    }

    LiveData<List<Alarm>> getAllAlarms() {return mAllAlarms;}

    public void insert(Alarm alarm){mRepository.insert(alarm);}

    public void update(Alarm alarm){mRepository.update(alarm);}
}
