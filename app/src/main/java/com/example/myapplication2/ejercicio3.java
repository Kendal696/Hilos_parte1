package com.example.myapplication2;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ejercicio3 extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendButton;
    private TextView receivedMessageTextView;

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        messageEditText = new EditText(this);
        messageEditText.setHint("Escribe un mensaje");
        layout.addView(messageEditText);

        sendButton = new Button(this);
        sendButton.setText("Enviar Mensaje");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                sendMessageToSecondActivity(message);
            }
        });
        layout.addView(sendButton);

        receivedMessageTextView = new TextView(this);
        receivedMessageTextView.setTextSize(18);
        layout.addView(receivedMessageTextView);

        setContentView(layout);

        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    String receivedMessage = (String) msg.obj;
                    receivedMessageTextView.setText("Mensaje Recibido: " + receivedMessage);
                }
            }
        };
    }

    private void sendMessageToSecondActivity(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }
}