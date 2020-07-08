package com.benasvolkovas.quickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SingleHabitActivity extends AppCompatActivity implements AddHabitDialog.HabitDialogListener {

    private TextView textView;
    private TextView streakView;
    private int id;
    private String content;
    private int streak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_habit);

        Intent getIntent = getIntent();
        textView = findViewById(R.id.habit_text);
        streakView = findViewById(R.id.habit_streak);

        // Gets extras from intent
        content = getIntent.getStringExtra("content");
        id = getIntent.getIntExtra("id", 0);
        streak = HabitsActivity.habitsDatabase.habitDao().getStreak(id);

        textView.setText(content);
        streakView.setText("Current streak:\n" + streak);

        // Deletes habit
        FloatingActionButton fabd = findViewById(R.id.delete_habit_button);
        fabd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitsActivity.habitsDatabase.habitDao().delete(id);

                // Returns to previous activity
                finish();
                overridePendingTransition(0,0);
            }
        });

        // Starts Habits Activity
        FloatingActionButton fabs = findViewById(R.id.save_habit_button);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(), HabitsActivity.class);
                context.startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        // Opens dialog
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    // Creates and shows dialog
    public void openDialog() {
        AddHabitDialog addHabitDialog = new AddHabitDialog();
        addHabitDialog.show(getSupportFragmentManager(), "habit dialog");
    }

    // Applies text
    @Override
    public void applyHabit(String text) {
        textView.setText(text);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Saves goal
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        HabitsActivity.habitsDatabase.habitDao().saveText(textView.getText().toString(), id);
    }
}
