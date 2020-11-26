
package com.example.aralarm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aralarm.ApplicationClass;
import com.example.aralarm.R;
import com.unity3d.player.UnityPlayerActivity;

public class GameActivity extends AppCompatActivity {

    private ApplicationClass applicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        applicationClass = (ApplicationClass)getApplicationContext();
        Button btn_game = findViewById(R.id.btn_game);
        btn_game.setOnClickListener(v -> {

            Intent intent = new Intent(GameActivity.this, UnityPlayerActivity.class);
            startActivityForResult(intent, 1215);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1234);
        finish();
    }
}