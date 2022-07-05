package com.example.themostusefulapp.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.themostusefulapp.data.Todo;
import com.example.themostusefulapp.viewholders.TodoViewHolder;

public class TodoListAdapter extends ListAdapter<Todo, TodoViewHolder> {
    public interface TodoListAdapterListener {
        void updateTodoItem(Todo todo);
    }

    private final TodoListAdapterListener listener;

    public TodoListAdapter(@NonNull DiffUtil.ItemCallback<Todo> diffCallback, TodoListAdapterListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TodoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo current = getItem(position);
        holder.bind(current, listener);
    }

    public static class TodoDiff extends DiffUtil.ItemCallback<Todo> {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getContent().equals(newItem.getContent())
                    && oldItem.getChecked() == newItem.getChecked();
        }
    }
}