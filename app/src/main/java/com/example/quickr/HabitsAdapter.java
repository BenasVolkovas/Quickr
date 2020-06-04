package com.example.quickr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// HabitsAdapter represents single task
public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitViewHolder> {

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView textView;
        private CheckBox checkBox;
        private TextView streakNum;
        public int id;

        public HabitViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.task_row);
            this.textView = view.findViewById(R.id.habit_text);
            this.checkBox = view.findViewById(R.id.habit_checkbox);
            this.streakNum = view.findViewById(R.id.habit_streak);
        }
    }

    // Creates list for habits
    private List<Habit> habits = new ArrayList<>();

    // While view is creating it converts xml to java code
    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_row, parent, false);

        return new HabitViewHolder(view);
    }

    // When view is created
    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        // Gets current habit
        Habit current = habits.get(position);

        // Sets tags to current view
        holder.containerView.setTag(current);
        holder.checkBox.setTag(current);

        // Sets text to current info saved in database
        // Streak number is hard-coded, because it is not in database
        holder.textView.setText(current.content);
        holder.streakNum.setText("999");
    }

    // Returns size of habits
    @Override
    public int getItemCount() {
        return habits.size();
    }

    // Gets all info from habits list and notifies that data has changed
    public void reload() {
        try {
            habits = HabitsActivity.habitsDatabase.habitDao().getAll();
        } catch (Exception error) {
            System.out.println("DB ERROR CATCH THAT PREVENTS CRASH ");
            System.out.println(error.toString());
        }
        notifyDataSetChanged();
    }
}
