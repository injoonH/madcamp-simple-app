package com.example.themostusefulapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @NonNull
    @ColumnInfo(name = "content")
    private final String content;

    @ColumnInfo(name = "is_checked")
    private boolean isChecked;

    public Todo(@NonNull String content, boolean isChecked) {
        this.content = content;
        this.isChecked = isChecked;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @NonNull
    @Override
    public String toString() {
        return "Todo{" +
                "uid=" + uid +
                ", content='" + content + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}