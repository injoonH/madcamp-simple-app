package com.example.themostusefulapp.viewholders;

import com.example.themostusefulapp.R;
import com.example.themostusefulapp.adapters.TodoListAdapter;
import com.example.themostusefulapp.data.Todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    private final CheckBox todoItemView;

    private TodoViewHolder(View itemView) {
        super(itemView);
        todoItemView = itemView.findViewById(R.id.todoItem);
    }

    public void bind(Todo todoItem, TodoListAdapter.TodoListAdapterListener listener) {
        String content = todoItem.getContent();
        boolean isChecked = todoItem.getChecked();

        Log.d("YUWOL", "Binding: " + todoItem);

        todoItemView.setText(content);
        todoItemView.setChecked(isChecked);
        todoItemView.setOnClickListener(view -> {
            if (isChecked) {
                Log.d("YUWOL", "Check box unchecked (" + content + ")");
            } else {
                Log.d("YUWOL", "Check box checked (" + content + ")");
            }
            todoItem.setChecked(!isChecked);
            listener.updateTodoItem(todoItem);
        });
    }

    public static TodoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(view);
    }
}