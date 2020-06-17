package com.example.quickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScoreActivity extends AppCompatActivity {
    private TextView textView;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        textView = findViewById(R.id.score_text);

        // If DB is not created then shows 0
        try {
            score = MainActivity.scoresDatabase.scoreDao().getAll().points;
        } catch (NullPointerException e) {

        }

        textView.setText(String.valueOf(score));

        // Returns to previous Activity
        FloatingActionButton fab = findViewById(R.id.return_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,0);
            }
        });
    }
}
