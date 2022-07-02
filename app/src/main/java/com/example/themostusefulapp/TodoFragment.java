package com.example.themostusefulapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private static final String TAG = "TodoFragment";

    RecyclerView recyclerView;
    TodoRecyclerAdapter adapter;
    Context context;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //fragment_todo 에 인플레이션을 함
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todo, container, false);
        initUI(rootView);

        loadTodoListData();


        //당겨서 새로고침
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTodoListData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }


    private void initUI(ViewGroup rootView){
        //RecyclerView 연결
        recyclerView = rootView.findViewById(R.id.recyclerview);

        //LinearLayoutManager을 이용하여 recyclerView 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 연결
        adapter = new TodoRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    public int loadTodoListData(){

        //데이터를 가져오는 sql문 select... (id의 역순으로 정렬)
        String loadSql = "select _id, TODO from " + TodoDatabase.TABLE_NOTE + " order by _id desc";

        int recordCount = -1;
        TodoDatabase database = TodoDatabase.getInstance(context);

        if(database != null){
            //cursor를 객체화하여 rawQuery문 저장
            Cursor outCursor = database.rawQuery(loadSql);

            recordCount = outCursor.getCount();

            //_id, TODO가 담겨질 배열 생성
            ArrayList<TodoData> items = new ArrayList<>();

            //for문을 통해 하나하나 추가
            for(int i = 0; i < recordCount; i++){
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String todo = outCursor.getString(1);
                items.add(new TodoData(todo, _id));
            }
            outCursor.close();

            //어댑터에 연결 및 데이터셋 변경
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }

        return recordCount;
    }

}
