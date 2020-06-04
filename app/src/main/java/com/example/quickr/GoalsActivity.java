package com.example.quickr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

// GoalActivity contains all goals in recycler view
public class GoalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GoalsAdapter adapter;
    public static GoalsDatabase goalsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        // Finds bottomNavigationView and sets listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_goals);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // If item is clicked, it starts activity
                switch (menuItem.getItemId()) {
                    case R.id.action_tasks:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.action_habits:
                        startActivity(new Intent(getApplicationContext(), HabitsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.action_goals:
                        return true;
                }
                return false;
            }
        });

        // Builds goalsDatabase
        goalsDatabase = Room
                .databaseBuilder(getApplicationContext(), GoalsDatabase.class, "goals")
                .allowMainThreadQueries()
                .build();


        recyclerView = findViewById(R.id.goals_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new GoalsAdapter();

        // Joins everything to recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Creates button for adding new goals
        FloatingActionButton fab = findViewById(R.id.add_goal_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalsDatabase.goalDao().create();
                adapter.reload();
            }
        });
    }

    // When brought to foreground reloads data
    @Override
    protected void onResume() {
        super.onResume();

        adapter.reload();
    }
}
