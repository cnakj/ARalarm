package com.example.aralarm;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.aralarm.SettingAlarmActivity.RETURN_ALARM;

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

        FloatingActionButton addButton = findViewById(R.id.btn_main_add);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingAlarmActivity.class);
            startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
        });

        adapter.setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(MainActivity.this, SettingAlarmActivity.class);
            Alarm alarm = adapter.getAlarm(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alarm", alarm);
            intent.putExtras(bundle);
            startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
        });

        adapter.setOnItemLongClickListener((v, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_alarm);
            builder.setPositiveButton(R.string.delete_ok, (dialog, which) -> {
                Alarm alarm = adapter.getAlarm(position);
                mAlarmViewModel.delete(alarm);
            });
            builder.setNegativeButton(R.string.delete_no, (dialog, which) -> {
                //취소
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        adapter.setOnSwitchClickListener((v, position) -> {
            Alarm alarm = adapter.getAlarm(position);
            alarm.toggle(!alarm.isOn());
            mAlarmViewModel.update(alarm);
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

            Toast.makeText(getApplicationContext(), R.string.main_saved, Toast.LENGTH_LONG).show();
        }
        else if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), R.string.main_not_saved, Toast.LENGTH_LONG).show();
        }
    }

}