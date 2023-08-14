package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ejercicio1 extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateDownloadWithThread();
                simulateDownloadWithCoroutine();
            }
        });
    }

    private void simulateDownloadWithThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Descargando imagen con Thread...");
                try {
                    Thread.sleep(3000); // Simulamos la descarga de 3 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Descarga de imagen con Thread completada");
            }
        });
        thread.start();
    }

    private void simulateDownloadWithCoroutine() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Descargando imagen con Coroutine...");
                try {
                    Thread.sleep(2000); // Simulamos la descarga de 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Descarga de imagen con Coroutine completada");
            }
        }).start();
    }
}