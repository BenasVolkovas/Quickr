package com.example.quickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskActivity extends AppCompatActivity {
    private EditText editText;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent getIntent = getIntent();
        editText = findViewById(R.id.task_edit_text);
        editText.setText(getIntent.getStringExtra("content"));
        id = getIntent.getIntExtra("id", 0);

        FloatingActionButton fab = findViewById(R.id.delete_task_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.database.taskDao().delete(id);

                finish();
            }
        });
    }


    // When activity is closed it will save changed task
    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        MainActivity.database.taskDao().save(editText.getText().toString(), id);
    }
}