package com.example.aralarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.aralarm.SettingAlarmActivity.RETURN_ALARM;

public class MainActivity extends AppCompatActivity {

    private AlarmViewModel mAlarmViewModel;
    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int CHANGE_ALARM_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.main_recyclerView);
        final AlarmListAdapter adapter = new AlarmListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        mAlarmViewModel.getAllAlarms().observe(this, alarms -> adapter.setAlarms(alarms));

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
            builder.setMessage("알람을 삭제합니다");
            builder.setPositiveButton("확인", (dialog, which) -> {
                Alarm alarm = adapter.getAlarm(position);
                mAlarmViewModel.delete(alarm);
            });
            builder.setNegativeButton("취소", (dialog, which) -> {
                //취소
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
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
        }
        else if(resultCode == RESULT_CANCELED){
            Toast.makeText(
                    getApplicationContext(),
                    R.string.main_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}