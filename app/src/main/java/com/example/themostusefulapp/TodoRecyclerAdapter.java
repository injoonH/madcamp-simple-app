package com.example.themostusefulapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>{


    ArrayList<TodoData> items = new ArrayList<TodoData>();

    @NonNull
    @Override
    public TodoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item을 inflate해주는 부분
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecyclerAdapter.ViewHolder holder, int position) {
        //item을 하나한 보여주는 부분.
        // 배열의 특정 위치에 있는 아이템 텍스트를 가져와서 실제 데이터를 하나하나 item 변수에 넣어줌.
        //ViewHolder의 holder에 그 텍스트를 넣어주고 다시 setLayout함.
        TodoData item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return items.size();
    }



    //RecyclerView의 핵심인 ViewHolder 클래스
    static class ViewHolder extends RecyclerView.ViewHolder{

        //todo_item.xml 에 세팅되어 있는 애들
        LinearLayout layoutTodo;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(View itemView){
            super(itemView);
            //todo_item.xml 에 있는 아이디를 가져와서 연결해줌
            layoutTodo = itemView.findViewById(R.id.layouttodo);
            checkBox = itemView.findViewById(R.id.checkbox);
            deleteButton = itemView.findViewById(R.id.deletebutton);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checkBox에서 텍스트를 가져와서 TODO에 저장 후 삭제
                    String TODO = (String) checkBox.getText();
                    deleteToDo(TODO);
                    Toast.makeText(v.getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }

                Context context;

                private void deleteToDo(String TODO){
                    //데이터베이스의 테이블을 삭제하는 sql문문
                   String deleteSql = "delete from " + TodoDatabase.TABLE_NOTE + " where " + "  TODO = '" + TODO+"'";
                    TodoDatabase database = TodoDatabase.getInstance(context);
                    database.execSQL(deleteSql);
                }
            });

        }
    }
}


