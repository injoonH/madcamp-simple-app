package com.example.themostusefulapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private final TodoDao mTodoDao;
    private final LiveData<List<Todo>> mAllTodo;

    TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodo = mTodoDao.getAllTodo();
    }

    LiveData<List<Todo>> getAllTodo() {
        return mAllTodo;
    }

    void insert(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> mTodoDao.insert(todo));
    }

    void update(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> mTodoDao.update(todo));
    }

    void delete(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> mTodoDao.delete(todo));
    }

    void deleteAll() {
        TodoRoomDatabase.databaseWriteExecutor.execute(mTodoDao::deleteAll);
    }
}