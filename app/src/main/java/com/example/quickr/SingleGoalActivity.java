package com.example.quickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SingleGoalActivity extends AppCompatActivity implements AddGoalDialog.GoalDialogListener{

    private CoordinatorLayout containerView;
    private TextView textView;
    private TextView timeView;
    private int id;
    private String content;
    private String time;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_goal);

        // Gets extras from intent
        Intent getIntent = getIntent();
        textView = findViewById(R.id.goal_text);
        timeView = findViewById(R.id.goal_time);

        content = getIntent.getStringExtra("content");
        id = getIntent.getIntExtra("id", 0);
        time = getIntent.getStringExtra("time");
        currentDate = getCurrentDate();

        textView.setText(content);
        timeView.setText("Achieve until\n" + time);

        // Creates button for deleting goals
        FloatingActionButton fabd = findViewById(R.id.delete_goal_button);
        fabd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalsActivity.goalsDatabase.goalDao().delete(id);

                // Returns to previous activity
                finish();
            }
        });

        // Creates button for saving goals
        FloatingActionButton fabs = findViewById(R.id.save_goal_button);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(), GoalsActivity.class);
                context.startActivity(intent);
            }
        });

        // If view is clicked it opens dialog
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        // If view is clicked it opens GoalCalendarActivity
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), GoalCalendarActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("content", content);
                intent.putExtra("time", time);

                context.startActivity(intent);
            }
        });
    }

    // Creates and shows dialog
    public void openDialog() {
        AddGoalDialog addGoalDialog = new AddGoalDialog();
        addGoalDialog.show(getSupportFragmentManager(), "goal dialog");
    }

    // Sets text to text, which was written in dialog and sets new content value
    @Override
    public void applyTask(String text) {
        textView.setText(text);
        content = textView.getText().toString();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

        List<String> times = Arrays.asList(currentDate.split("/"));

        int currentDay = Integer.parseInt(times.get(1));
        int currentMonth = Integer.parseInt(times.get(0));
        int currentYear = Integer.parseInt(times.get(2)) + 2000;
        String date = currentYear + " / " + currentMonth + " / " + currentDay;

        return date;
    }

    // When activity is closed it saves changed goal
    @Override
    protected void onPause() {
        super.onPause();

        // Saves goal
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        GoalsActivity.goalsDatabase.goalDao().saveText(textView.getText().toString(), id);
    }
}
