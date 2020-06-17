package com.example.quickr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

// TasksAdapter represents single task
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerView;
        private TextView titleTextView;
        public CheckBox checkBox;
        public int id;

        public TaskViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.task_row);
            this.titleTextView = view.findViewById(R.id.task_row_title);
            this.checkBox = view.findViewById(R.id.task_checkbox);

            // When container is clicked it puts Extra to intent and starts TasksActivity
            this.containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = (Task) containerView.getTag();
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(), TaskActivity.class);
                    intent.putExtra("id", task.id);
                    intent.putExtra("content", task.content);

                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }
            });

            // When checkbox is clicked it deletes task and reloads MainActivity
            this.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Task task = (Task) checkBox.getTag();
                    Context context = buttonView.getContext();
                    Intent intent = new Intent(buttonView.getContext(), MainActivity.class);
                    MainActivity.tasksDatabase.taskDao().delete(task.id);

                    try {
                        Cursor cursor = MainActivity.scoresDatabase.query("SELECT * FROM scores", null);
                        if (cursor.getCount() == 0) {
                            MainActivity.scoresDatabase.scoreDao().create();
                            MainActivity.scoresDatabase.scoreDao().updatePoints(100);
                        } else {
                            MainActivity.scoresDatabase.scoreDao().updatePoints(100);
                        }
                    } catch (NullPointerException e) {

                    }

                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }
            });
        }
    }

    // Creates list for tasks
    private List<Task> tasks = new ArrayList<>();

    // While view is creating it converts xml to java code
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(view);
    }

    // When view is created
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        // Gets current task
        Task current = tasks.get(position);

        // Sets tags to current view
        holder.containerView.setTag(current);
        holder.checkBox.setTag(current);

        // Sets text to current info saved in database
        holder.titleTextView.setText(current.content);
    }

    // Returns size of tasks
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // Gets all info from database and notifies that data has changed
    public void reload() {
        tasks = MainActivity.tasksDatabase.taskDao().getAll();
        notifyDataSetChanged();
    }
}
