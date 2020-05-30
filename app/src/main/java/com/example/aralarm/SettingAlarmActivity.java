package com.example.aralarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class SettingAlarmActivity extends AppCompatActivity {

    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_TIME = "extra_time";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final DatePickerDialog datePicker;
        final TimePicker timePicker = findViewById(R.id.picker_setting_time);
        final TextView dateText = findViewById(R.id.txt_setting_calendar);
        ImageButton calendarButton = findViewById(R.id.btn_setting_calendar);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateText.setText(year+"년 " + (month+1) +"월 " +dayOfMonth+"일");
            }
        };

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Alarm item = (Alarm) bundle.getSerializable("item");
            timePicker.setHour(item.getHour());
            timePicker.setMinute(item.getMinute());
            dateText.setText(item.getMDate());
            datePicker = new DatePickerDialog(this, listener, item.getYear(), item.getMonth()-1, item.getDay());
        }
        else{
            datePicker = new DatePickerDialog(this);
            datePicker.setOnDateSetListener(listener);
        }
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();
            }
        });

        final Button cancelButton = findViewById(R.id.btn_setting_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });

        final Button saveButton = findViewById(R.id.btn_setting_save);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent replyIntent = new Intent();
                String date = "" + datePicker.getDatePicker().getYear()
                        + datePicker.getDatePicker().getMonth()
                        + datePicker.getDatePicker().getDayOfMonth();
                String time = ""+timePicker.getHour()+timePicker.getMinute();
                replyIntent.putExtra(EXTRA_DATE, date);
                replyIntent.putExtra(EXTRA_TIME, time);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}
