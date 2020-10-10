package com.example.aralarm.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aralarm.data.Alarm;
import com.example.aralarm.ui.AlarmListAdapter;
import com.example.aralarm.notification.AlarmReceiver;
import com.example.aralarm.ui.AlarmViewModel;
import com.example.aralarm.notification.DeviceBootReceiver;
import com.example.aralarm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import static com.example.aralarm.activity.SettingAlarmActivity.RETURN_ALARM;

public class MainActivity extends AppCompatActivity {

    private AlarmViewModel mAlarmViewModel;
    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int CHANGE_ALARM_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView onNumText =findViewById(R.id.txt_main_alarm_num);
        RecyclerView recyclerView = findViewById(R.id.main_recyclerView);
        final AlarmListAdapter adapter = new AlarmListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        mAlarmViewModel.getOnAlarms().observe(this, onNum -> {
            Resources res = getResources();
            if(onNum<=0)
                onNumText.setText(R.string.main_all_off);
            else
                onNumText.setText(String.format(res.getString(R.string.main_num_on),onNum));
        });

        mAlarmViewModel.getAllAlarms().observe(this, adapter::setAlarms);

        // 새로운 알람 추가
        FloatingActionButton addButton = findViewById(R.id.btn_main_add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingAlarmActivity.class);
            startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
        });

        // 기존 알람 변경
        adapter.setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(MainActivity.this, SettingAlarmActivity.class);
            Alarm alarm = adapter.getAlarm(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alarm", alarm);
            intent.putExtras(bundle);
            startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
        });

        // 알람 삭제
        adapter.setOnItemLongClickListener((v, position) -> {
            adapter.showCheckBox(true);


//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(R.string.delete_alarm);
//            builder.setPositiveButton(R.string.delete_ok, (dialog, which) -> {
//                Alarm alarm = adapter.getAlarm(position);
//                offAlarm(alarm);
//                mAlarmViewModel.delete(alarm);
//            });
//            builder.setNegativeButton(R.string.delete_no, (dialog, which) -> {
//                //취소
//            });
//
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();

        });

        // 스위치 토글
        adapter.setOnSwitchClickListener((v, position) -> {
            Alarm alarm = adapter.getAlarm(position);
            alarm.toggle(!alarm.isOn());
            mAlarmViewModel.update(alarm);

            if(alarm.isOn())
                onAlarm(alarm);
            else
                offAlarm(alarm);
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bundle returnBundle = data.getExtras();
            assert returnBundle != null;
            Alarm alarm = (Alarm) returnBundle.getSerializable(RETURN_ALARM);
            if(requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE)
                mAlarmViewModel.insert(alarm);
            else if(requestCode == CHANGE_ALARM_ACTIVITY_REQUEST_CODE)
                mAlarmViewModel.update(alarm);

            assert alarm != null;
            onAlarm(alarm);
        }
        else if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), R.string.main_not_saved, Toast.LENGTH_SHORT).show();
        }
    }


    public void onAlarm(Alarm alarm){
        Calendar calendar = Calendar.getInstance();
        calendar.set(alarm.getIntYear(), alarm.getIntMonth() - 1, alarm.getIntDay(), alarm.getIntHour(), alarm.getIntMinute(), 0);

        Log.i("MYAPP", "알람 설정할 떄 계산한 id는 " + alarm.getPendingID());
        setAlarmNotification(calendar, alarm.getPendingID());
    }

    public void offAlarm(Alarm alarm){
        Log.i("MYAPP", "알람 끌때 계산한 id는 " + alarm.getPendingID());
        unsetAlarmNotification(alarm.getPendingID());
    }

    public void setAlarmNotification(Calendar calendar, int requestCode) {

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Log.i("MYAPP", "알람설정 id는 " + requestCode);
            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            Toast.makeText(getApplicationContext(), R.string.main_set, Toast.LENGTH_SHORT).show();
        }

    }



    public void unsetAlarmNotification(int requestCode){
        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, alarmIntent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.i("MYAPP", "알람취소 id는 " + requestCode);
        if (pendingIntent != null){
            alarmManager.cancel(pendingIntent);

            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            Toast.makeText(getApplicationContext(), R.string.main_unset, Toast.LENGTH_SHORT).show();
        }
    }

}