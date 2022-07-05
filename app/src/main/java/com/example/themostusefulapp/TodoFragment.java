package com.example.themostusefulapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.themostusefulapp.adapters.TodoListAdapter;
import com.example.themostusefulapp.data.Todo;
import com.example.themostusefulapp.data.TodoViewModel;

public class TodoFragment extends Fragment implements
        TodoFormDialogFragment.TodoDialogListener,
        TodoDeleteAllDialogFragment.TodoDeleteAllDialogListener,
        TodoListAdapter.TodoListAdapterListener {
    private TodoViewModel mTodoViewModel;
    private TodoListAdapter adapter;

    public TodoFragment() {
        super(R.layout.fragment_todo);
    }

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView todoRecyclerView = view.findViewById(R.id.todoRecView);
        adapter = new TodoListAdapter(new TodoListAdapter.TodoDiff(), this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(todoRecyclerView);
        todoRecyclerView.setAdapter(adapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final FloatingActionButton todoAddBtn = view.findViewById(R.id.todoAddBtn);
        todoAddBtn.setOnClickListener(v -> {
            TodoFormDialogFragment dialog = new TodoFormDialogFragment(this);
            dialog.show(getParentFragmentManager(), "todo_form_dialog");
        });

        final ImageView deleteAllBtn = view.findViewById(R.id.todoDeleteAllBtn);
        deleteAllBtn.setOnClickListener(v -> {
            TodoDeleteAllDialogFragment dialog = new TodoDeleteAllDialogFragment(this);
            dialog.show(getParentFragmentManager(), "todo_delete_all_dialog");
        });

        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        mTodoViewModel.getAllTodo().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Todo todoItem = adapter.getCurrentList().get(viewHolder.getAdapterPosition());
            Log.d("YUWOL", "onSwiped: " + todoItem);
            mTodoViewModel.delete(todoItem);
        }
    };

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        String todoContent = ((TodoFormDialogFragment) dialog).getTodoInputContent();
        mTodoViewModel.insert(new Todo(todoContent, false));
    }

    @Override
    public void onTodoDeleteAllPositiveClick() {
        mTodoViewModel.deleteAll();
    }

    @Override
    public void updateTodoItem(Todo todo) {
        mTodoViewModel.update(todo);
    }
}