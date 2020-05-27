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

// Main Activity is where all tasks will be presented
// This activity is shown first
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TasksAdapter adapter;
    public static TasksDatabase tasksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finds bottomNavigationView and sets listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_tasks);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // If item is clicked, it starts activity
                switch (menuItem.getItemId()) {
                    case R.id.action_tasks:
                        return true;

                    case R.id.action_habits:
                        startActivity(new Intent(getApplicationContext(), HabitsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.action_goals:
                        startActivity(new Intent(getApplicationContext(), GoalsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Builds tasksDatabase
        tasksDatabase = Room
                .databaseBuilder(getApplicationContext(), TasksDatabase.class, "tasks")
                .allowMainThreadQueries()
                .build();


        recyclerView = findViewById(R.id.tasks_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TasksAdapter();

        // Joins everything to recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        // Creates button for adding new tasks
        FloatingActionButton fab = findViewById(R.id.add_task_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasksDatabase.taskDao().create();
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