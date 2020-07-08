package com.benasvolkovas.quickr;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

// Dialog, which will be shown when task text is clicked in TaskActivity
public class AddTaskDialog extends AppCompatDialogFragment {
   private EditText editTextTask;
   private TaskDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Creates empty AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Converts xml file to java code
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_task_dialog, null);

        editTextTask = view.findViewById(R.id.task_dialog);

        // Sets dialog to listen, which button is pressed
        builder.setView(view)
                .setTitle("Task")
                // If cancel, nothing happens
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                // If create, applies written text to listener
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskText = editTextTask.getText().toString();
                        listener.applyTask(taskText);
                    }
                });

        // Returns created dialog
        return builder.create();
    }

    // Attaches context to listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (TaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement TaskDialogListener");
        }
    }

    // Gets text
    public interface TaskDialogListener {
        void applyTask(String text);
    }
}
