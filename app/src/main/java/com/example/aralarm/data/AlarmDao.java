package com.example.aralarm.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aralarm.data.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Alarm alarm);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table ORDER BY year, month, day, hour, minute")
    LiveData<List<Alarm>> getOrderedWords();

    @Query("SELECT COUNT(*) FROM alarm_table WHERE isOn = 1")
    LiveData<Integer> getOnAlarms();
}
