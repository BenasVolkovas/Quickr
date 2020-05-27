package com.example.quickr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// GoalsAdapter represents single task
public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalViewHolder> {

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;
        private TextView time;

        public GoalViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.goal_row);
            this.textView = view.findViewById(R.id.goal_text);
            this.time = view.findViewById(R.id.goal_time);
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

        // Sets tag to containerView
        holder.containerView.setTag(current);

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
