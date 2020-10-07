package com.example.aralarm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.aralarm.ApplicationClass;
import com.example.aralarm.R;

public class GameActivity extends AppCompatActivity {

    private ApplicationClass applicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        applicationClass = (ApplicationClass)getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Button btn_off = findViewById(R.id.btn_off);
        btn_off.setOnClickListener(v -> {
            notificationManager.cancel(1234);
            applicationClass.ringtone.stop();
            finish();
        });

        Button btn_game = findViewById(R.id.btn_game);
        btn_game.setOnClickListener(v -> {
            // unity 게임으로 연결되게.
        });

    }
}