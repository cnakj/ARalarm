package com.example.aralarm.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aralarm.data.Alarm;
import com.example.aralarm.R;
import com.example.aralarm.databinding.ActivitySettingBinding;

import java.util.UUID;

public class SettingAlarmActivity extends AppCompatActivity {
    private static ActivitySettingBinding binding;
    public static final String RETURN_ALARM = "return_alarm";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final DatePickerDialog datePicker = new DatePickerDialog(this);
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> binding.txtSettingCalendar.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
        datePicker.setOnDateSetListener(listener);

        Bundle bundle = getIntent().getExtras();
        String returnId;
        if(bundle != null){
            Alarm item = (Alarm) bundle.getSerializable("alarm");
            returnId = item.getId();
            binding.pickerSettingTime.setHour(item.getIntHour());
            binding.pickerSettingTime.setMinute(item.getIntMinute());
            binding.txtSettingCalendar.setText(item.getYear() + "년 " + item.getMonth() + "월 " + item.getDay() + "일");
            datePicker.updateDate(item.getIntYear(), item.getIntMonth()-1, item.getIntDay());
        }
        else{
            returnId = UUID.randomUUID().toString();
            binding.txtSettingCalendar.setText(datePicker.getDatePicker().getYear()+"년 "+(datePicker.getDatePicker().getMonth()+1)+"월 "+datePicker.getDatePicker().getDayOfMonth()+"일");
        }
        binding.btnSettingCalendar.setOnClickListener(v -> {
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();
        });

        binding.btnSettingCancel.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        binding.btnSettingSave.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            Bundle returnBundle = new Bundle();
            Alarm returnAlarm = new Alarm(returnId,
                    datePicker.getDatePicker().getYear(),
                    datePicker.getDatePicker().getMonth() + 1,
                    datePicker.getDatePicker().getDayOfMonth(),
                    binding.pickerSettingTime.getHour(),
                    binding.pickerSettingTime.getMinute(), true);
            returnBundle.putSerializable(RETURN_ALARM, returnAlarm);
            returnIntent.putExtras(returnBundle);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}
