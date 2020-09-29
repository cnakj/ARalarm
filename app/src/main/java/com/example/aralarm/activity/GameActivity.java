package com.example.aralarm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aralarm.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Button btn_off = findViewById(R.id.btn_off);
        btn_off.setOnClickListener(v -> {
            notificationManager.cancel(1234);
            Intent intent = new Intent();
            //intent.putExtra()
            finish();
        });

    }
}