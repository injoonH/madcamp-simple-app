package com.example.themostusefulapp.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final TodoRepository mRepository;
    private final LiveData<List<Todo>> mAllTodo;

    public TodoViewModel(Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodo = mRepository.getAllTodo();
    }

    public LiveData<List<Todo>> getAllTodo() {
        return mAllTodo;
    }

    public void insert(Todo todo) {
        mRepository.insert(todo);
    }

    public void update(Todo todo) {
        mRepository.update(todo);
    }

    public void delete(Todo todo) {
        mRepository.delete(todo);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}