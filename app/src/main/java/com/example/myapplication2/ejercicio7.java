package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ejercicio7 extends AppCompatActivity {

    private EditText taskEditText;
    private Button addButton;
    private ListView taskListView;

    private ExecutorService executorService;
    private List<String> taskList;
    private ArrayAdapter<String> taskListAdapter;

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio7);

        taskEditText = findViewById(R.id.taskEditText);
        addButton = findViewById(R.id.addButton);
        taskListView = findViewById(R.id.taskListView);

        mainHandler = new Handler();

        executorService = Executors.newSingleThreadExecutor();
        taskList = new ArrayList<>();
        taskListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        taskListView.setAdapter(taskListAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    addTask(task);
                }
            }
        });
    }

    private void addTask(final String task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskList.add(task);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskListAdapter.notifyDataSetChanged();
                        taskEditText.setText("");
                        Toast.makeText(ejercicio7.this, "Tarea agregada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}