package com.example.themostusefulapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TodoFormDialogFragment extends DialogFragment {
    public interface TodoDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    private final TodoDialogListener listener;
    private String todoInputContent;

    public TodoFormDialogFragment(TodoDialogListener listener) {
        this.listener = listener;
        this.todoInputContent = "";
    }

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final EditText todoInput = new EditText(getContext());
        todoInput.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create Todo")
                .setView(todoInput)
                .setPositiveButton("Submit", (dialogInterface, i) -> {
                    todoInputContent = todoInput.getText().toString();
                    Log.d("YUWOL", "New todo item submitted: " + todoInputContent);
                    if (todoInputContent.equals(""))
                        return;
                    listener.onDialogPositiveClick(TodoFormDialogFragment.this);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                });

        return builder.create();
    }

    public String getTodoInputContent() {
        return todoInputContent;
    }
}