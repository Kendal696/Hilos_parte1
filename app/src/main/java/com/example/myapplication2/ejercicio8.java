package com.example.myapplication2;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ejercicio8 extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private Button processButton;
    private ProgressBar progressBar;

    private ExecutorService executorService;
    private List<Bitmap> processedImages;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio8);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        processButton = findViewById(R.id.processButton);
        progressBar = findViewById(R.id.progressBar);

        mainHandler = new Handler();
        executorService = Executors.newFixedThreadPool(2); // Two threads for parallel processing
        processedImages = new ArrayList<>();

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImages();
            }
        });
    }

    private void processImages() {
        progressBar.setVisibility(View.VISIBLE);
        processedImages.clear();

        for (int i = 0; i < 2; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gatito);
                    Bitmap processedBitmap = processImage(originalBitmap);
                    processedImages.add(processedBitmap);

                    if (index == 1) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayProcessedImages();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });
        }
    }

    private Bitmap processImage(Bitmap originalBitmap) {
        // Simulated image processing (grayscale conversion)
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap processedBitmap = Bitmap.createBitmap(width, height, originalBitmap.getConfig());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = originalBitmap.getPixel(x, y);
                int grayValue = (int) (0.2989 * Color.red(pixel) + 0.5870 * Color.green(pixel) + 0.1140 * Color.blue(pixel));
                processedBitmap.setPixel(x, y, Color.rgb(grayValue, grayValue, grayValue));
            }
        }

        return processedBitmap;
    }

    private void displayProcessedImages() {
        if (processedImages.size() >= 2) {
            imageView1.setImageBitmap(processedImages.get(0));
            imageView2.setImageBitmap(processedImages.get(1));
        }
    }
}