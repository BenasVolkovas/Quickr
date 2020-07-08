package com.benasvolkovas.quickr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class GoalCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView textView;
    private Button button;
    private int id;
    private String content;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_calendar);

        // Gets info from Single Goal Activity
        Intent getIntent = getIntent();
        content = getIntent.getStringExtra("content");
        id = getIntent.getIntExtra("id", 0);
        time = getIntent.getStringExtra("time");

        calendarView = findViewById(R.id.calendar_date);
        textView = findViewById(R.id.date_text);
        button = findViewById(R.id.save_goal_date);

        // When clicked on date, sets time = date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                time = year + " / " + (month + 1) + " / " + dayOfMonth;
                textView.setText(time);
            }
        });

        // When clicked it starts Single Goal Activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), SingleGoalActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("content", content);
                intent.putExtra("time", time);

                context.startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    // When activity is closed it saves changed goal
    @Override
    protected void onPause() {
        super.onPause();

        // Saves goal
        GoalsActivity.goalsDatabase.goalDao().saveDate(time, id);
        GoalsActivity.goalsDatabase.goalDao().saveText(content, id);
    }
}
