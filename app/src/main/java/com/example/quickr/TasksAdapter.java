package com.example.quickr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

// Task Adapter represents single task
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView titleTextView;

        public TaskViewHolder(View view) {
            super(view);
            this.containerView = view.findViewById(R.id.task_row);
            this.titleTextView = view.findViewById(R.id.task_row_title);

            // When container is clicked it puts Extra to intent and starts Tasks Activity
            this.containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Task task = (Task) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), TaskActivity.class);
                    intent.putExtra("id", task.id);
                    intent.putExtra("content", task.content);

                    context.startActivity(intent);
                }
            });
        }
    }


    private List<Task> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.containerView.setTag(current);
        holder.titleTextView.setText(current.content);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // It gets everything from database and notifies that data has changed
    public void reload() {
        tasks = MainActivity.database.taskDao().getAll();
        notifyDataSetChanged();
    }
}
