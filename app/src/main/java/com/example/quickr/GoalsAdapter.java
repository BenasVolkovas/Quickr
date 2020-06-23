package com.example.quickr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

// GoalsAdapter represents single task
public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalViewHolder> {

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;
        private TextView time;
        private CheckBox checkBox;

        // Constructor
        public GoalViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.goal_row);
            this.textView = view.findViewById(R.id.goal_text);
            this.time = view.findViewById(R.id.goal_time);
            this.checkBox = view.findViewById(R.id.goal_checkbox);

            // When container is clicked it puts Extra to intent and starts GoalsActivity
            this.containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goal goal = (Goal) containerView.getTag();
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(), SingleGoalActivity.class);
                    intent.putExtra("id", goal.id);
                    intent.putExtra("content", goal.content);
                    intent.putExtra("time", goal.time);

                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }
            });

            // When checkbox is clicked it deletes goal and reloads GoalsActivity
            this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    final CompoundButton buttonViewFinal = buttonView;

                    // Creates handler for adding delay
                    Handler handler = new Handler();

                    // Method with delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Deletes goal by finding tag
                            Goal goal = (Goal) checkBox.getTag();
                            Context context = buttonViewFinal.getContext();
                            Intent intent = new Intent(buttonViewFinal.getContext(), GoalsActivity.class);
                            GoalsActivity.goalsDatabase.goalDao().delete(goal.id);

                            // If scores DB is empty it creates one column
                            try {
                                Cursor cursor = MainActivity.scoresDatabase.query("SELECT * FROM scores", null);
                                if (cursor.getCount() == 0) {
                                    MainActivity.scoresDatabase.scoreDao().create();
                                    MainActivity.scoresDatabase.scoreDao().updatePoints(200);
                                } else {
                                    MainActivity.scoresDatabase.scoreDao().updatePoints(200);
                                }
                            } catch (NullPointerException e) {

                            }

                            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                        }
                        // Dealy
                    }, 300);
                }
            });
        }
    }

    // Creates list for goals
    private List<Goal> goals = new ArrayList<>();

    // While view is creating it converts xml to java code
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_row, parent, false);

        return new GoalViewHolder(view);
    }

    // When view is created
    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        // Gets current goal
        Goal current = goals.get(position);

        // Sets tags to current view
        holder.containerView.setTag(current);
        holder.checkBox.setTag(current);

        // Sets text to current info saved in database
        holder.textView.setText(current.content);
        holder.time.setText(current.time);
    }

    // Returns size of goals
    @Override
    public int getItemCount() {
        return goals.size();
    }



    // Gets all info from goals list and notifies that data has changed
    public void reload() {
        goals = GoalsActivity.goalsDatabase.goalDao().getAll();
        notifyDataSetChanged();
    }
}
