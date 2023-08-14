package com.example.myapplication2;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ejercicio6 extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView statusTextView;
    private Button startButton;

    private Handler mainHandler;
    private ExecutorService executorService;
    private boolean isDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio6);

        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);
        startButton = findViewById(R.id.startButton);

        mainHandler = new Handler();

        executorService = Executors.newSingleThreadExecutor();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDownloading) {
                    startDownload();
                } else {
                    stopDownload();
                }
            }
        });
    }

    private void startDownload() {
        isDownloading = true;
        startButton.setText("Detener Descarga");
        progressBar.setProgress(0);
        statusTextView.setText("Descargando...");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int progress = 0; progress <= 100; progress += 5) {
                    simulateDownload();
                    final int finalProgress = progress;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(finalProgress);
                        }
                    });

                    if (!isDownloading) {
                        break;
                    }
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        finishDownload();
                    }
                });
            }
        });
    }

    private void stopDownload() {
        isDownloading = false;
        startButton.setText("Iniciar Descarga");
        statusTextView.setText("Descarga detenida");
    }

    private void simulateDownload() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(int progress) {
        progressBar.setProgress(progress);
        statusTextView.setText("Descargando... " + progress + "%");
    }

    private void finishDownload() {
        isDownloading = false;
        startButton.setText("Iniciar Descarga");
        progressBar.setProgress(100);
        statusTextView.setText("Descarga completada");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}