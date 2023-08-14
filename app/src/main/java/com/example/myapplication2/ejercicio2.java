package com.example.myapplication2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ejercicio2 extends AppCompatActivity {

    private TextView messageTextView;
    private EditText messageEditText;
    private Button sendButton;

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        messageTextView = findViewById(R.id.messageTextView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String message = (String) msg.obj;
                    updateUIWithMessage(message);
                }
            }
        };

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                sendMessage(message);
                messageEditText.setText("");
            }
        });
    }

    private void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Simulamos el envío del mensaje (3 segundos)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Enviamos el mensaje al otro dispositivo simulando una respuesta
                String response = "Respuesta: " + message;
                Message msg = mainHandler.obtainMessage(1, response);
                mainHandler.sendMessage(msg);
            }
        }).start();
    }

    private void updateUIWithMessage(String message) {
        messageTextView.append("\n" + message);
    }
}