package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

public class ejercicio4 extends AppCompatActivity {

    private Button startServiceButton;
    private Button stopServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);

        startServiceButton = findViewById(R.id.startServiceButton);
        stopServiceButton = findViewById(R.id.stopServiceButton);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundService();
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBackgroundService();
            }
        });
    }

    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        startService(serviceIntent);
    }

    private void stopBackgroundService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        stopService(serviceIntent);
    }

    public static class TimerService extends Service {

        private static final String CHANNEL_ID = "TimerServiceChannel";
        private static final int NOTIFICATION_ID = 123;

        private boolean isServiceRunning = false;
        private int secondsCounter = 0;

        private Handler timerHandler = new Handler();
        private Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                secondsCounter++;
                timerHandler.postDelayed(this, 1000);
            }
        };

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (!isServiceRunning) {
                startTimer();
                isServiceRunning = true;
            }
            return START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopTimer();
            isServiceRunning = false;
        }

        private void startTimer() {
            timerHandler.postDelayed(timerRunnable, 1000);
        }

        private void stopTimer() {
            timerHandler.removeCallbacks(timerRunnable);
        }



    }
}