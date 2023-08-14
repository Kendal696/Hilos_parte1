package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ejercicio5 extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;

    private long startTime = 0L;
    private long elapsedTime = 0L;
    private long updatedTime = 0L;

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updatedTime = elapsedTime;
            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerTextView.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
            timerHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio5);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }

    private void pauseTimer() {
        if (startTime != 0L) {
            timerHandler.removeCallbacks(timerRunnable);
            elapsedTime = System.currentTimeMillis() - startTime;
            updatedTime = elapsedTime;
            startTime = 0L;
        }
    }

    private void resetTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        timerTextView.setText("00:00:000");
        startTime = 0L;
        elapsedTime = 0L;
        updatedTime = 0L;
    }
}