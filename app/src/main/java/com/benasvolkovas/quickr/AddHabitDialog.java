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

public class AddHabitDialog extends AppCompatDialogFragment {
    private EditText editTextTask;
    private HabitDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Creates empty AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Converts xml file to java code
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_habit_dialog, null);

        editTextTask = view.findViewById(R.id.habit_dialog);

        builder.setView(view)
                .setTitle("Habit")
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
                        String habitText = editTextTask.getText().toString();
                        listener.applyHabit(habitText);
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
            listener = (HabitDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement HabitDialogListener");
        }
    }

    // Gets text
    public interface HabitDialogListener {
        void applyHabit(String text);
    }
}
