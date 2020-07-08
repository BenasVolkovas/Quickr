package com.benasvolkovas.quickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskActivity extends AppCompatActivity implements AddTaskDialog.TaskDialogListener {
    private CoordinatorLayout containerView;
    private TextView textView;
    private int id;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Gets extras from intent
        Intent getIntent = getIntent();
        textView = findViewById(R.id.task_text);
        content = getIntent.getStringExtra("content");
        textView.setText(content);
        id = getIntent.getIntExtra("id", 0);

        // Creates button for deleting tasks
        FloatingActionButton fabd = findViewById(R.id.delete_task_button);
        fabd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.tasksDatabase.taskDao().delete(id);

                // Returns to previous activity
                finish();
                overridePendingTransition(0,0);
            }
        });

        // Creates button for saving tasks
        FloatingActionButton fabs = findViewById(R.id.save_task_button);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Saving is done when TaskActivity is closed, so it needs to just return to MainActivity
                finish();
                overridePendingTransition(0,0);
            }
        });

        // Finds containerView
        // If view is clicked it opens dialog
        containerView = findViewById(R.id.single_task_container);
        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    // Creates and shows dialog
    public void openDialog() {
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.show(getSupportFragmentManager(), "task dialog");
    }

    // Sets text to text, which was written in dialog
    @Override
    public void applyTask(String text) {
        textView.setText(text);
    }

    // When activity is closed it saves changed task
    @Override
    protected void onPause() {
        super.onPause();

        // Saves task
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        MainActivity.tasksDatabase.taskDao().save(textView.getText().toString(), id);
    }
}