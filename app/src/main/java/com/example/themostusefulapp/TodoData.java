package com.example.themostusefulapp;

public class TodoData {
    String todo;
    //데이터베이스를 만들 때 만든 id대로 정렬할 때 사용
    int id;

    public TodoData(String todo, int id) {
        this.todo = todo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
