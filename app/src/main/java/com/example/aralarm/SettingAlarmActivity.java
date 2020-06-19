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

    public static final String EXTRA_YEAR = "extra_year";
    public static final String EXTRA_MONTH = "extra_month";
    public static final String EXTRA_DAY = "extra_day";
    public static final String EXTRA_HOUR = "extra_hour";
    public static final String EXTRA_MINUTE = "extra_minute";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final TimePicker timePicker = findViewById(R.id.picker_setting_time);
        final TextView dateText = findViewById(R.id.txt_setting_calendar);
        ImageButton calendarButton = findViewById(R.id.btn_setting_calendar);
        final DatePickerDialog datePicker = new DatePickerDialog(this);
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth)
                -> dateText.setText(year+"년 " + (month+1) +"월 " +dayOfMonth+"일");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Alarm item = (Alarm) bundle.getSerializable("item");
            timePicker.setHour(Integer.parseInt(item.getHour()));
            timePicker.setMinute(Integer.parseInt(item.getMinute()));
            dateText.setText(item.getYear() + "년 " + item.getMonth() + "월 " + item.getDay() + "일");
            datePicker.updateDate(Integer.parseInt(item.getYear()), Integer.parseInt(item.getMonth())-1, Integer.parseInt(item.getDay()));
        }
        else{
            dateText.setText(datePicker.getDatePicker().getYear()+"년 "+(datePicker.getDatePicker().getMonth()+1)+"월 "+datePicker.getDatePicker().getDayOfMonth()+"일");
        }
        calendarButton.setOnClickListener(v -> {
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();
        });

        final Button cancelButton = findViewById(R.id.btn_setting_cancel);
        cancelButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        final Button saveButton = findViewById(R.id.btn_setting_save);
        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            String year = "" + datePicker.getDatePicker().getYear();
            String month = "" + (datePicker.getDatePicker().getMonth() + 1);
            String day = "" + datePicker.getDatePicker().getDayOfMonth();
            String hour = "" + timePicker.getHour();
            String minute = "" + timePicker.getMinute();
            replyIntent.putExtra(EXTRA_YEAR, year);
            replyIntent.putExtra(EXTRA_MONTH, month);
            replyIntent.putExtra(EXTRA_DAY, day);
            replyIntent.putExtra(EXTRA_HOUR, hour);
            replyIntent.putExtra(EXTRA_MINUTE, minute);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}
