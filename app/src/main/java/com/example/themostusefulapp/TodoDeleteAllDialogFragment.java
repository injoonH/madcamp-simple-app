package com.example.themostusefulapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

public class TodoDeleteAllDialogFragment extends DialogFragment {
    public interface TodoDeleteAllDialogListener {
        void onTodoDeleteAllPositiveClick();
    }

    private final TodoDeleteAllDialogListener listener;

    public TodoDeleteAllDialogFragment(TodoDeleteAllDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete all todos?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Log.d("YUWOL", "Delete all todo items");
                    listener.onTodoDeleteAllPositiveClick();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                });

        return builder.create();
    }
}