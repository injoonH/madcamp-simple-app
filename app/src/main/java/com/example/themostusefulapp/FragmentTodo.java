package com.example.themostusefulapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentTodo extends Fragment {

    private static final String TAG = "fragment_todo";

    Fragment mainFragment;
    EditText inputToDo;

    public FragmentTodo(){super(R.layout.fragment_todo);}


    public static TodoDatabase TodoDatabase = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mainFragment = new TodoFragment();

        /*FragmentActivity activity = null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.todoframelayout, mainFragment).commit();

         */
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context=view.getContext();

        Button saveButton=view.findViewById(R.id.savebutton);

        saveButton.setOnClickListener(v -> {
            Toast.makeText(context.getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            saveToDo(context);
        });

        openDatabase(context);
    }

        public void openDatabase(Context context) {
        // open database
        if (TodoDatabase != null) {
            TodoDatabase.close();
            TodoDatabase = null;
        }
        TodoDatabase = TodoDatabase.getInstance(context);
        boolean isOpen = TodoDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Note database is open.");
        } else {
            Log.d(TAG, "Note database is not open.");
        }
    }

        private void saveToDo(Context context){
        View view;
        inputToDo = getView().findViewById(R.id.inputtodo);

        //EditText에 적힌 글을 가져오기
        String todo = inputToDo.getText().toString();

        if(todo.equals("")) {
            Toast.makeText(context, "할 일을 적어주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        //테이블에 값을 추가하는 sql구문 insert...
        String sqlSave = "insert into " + com.example.themostusefulapp.TodoDatabase.TABLE_NOTE + " (TODO) values (" +
                "'" + todo + "')";

        //sql문 실행
        TodoDatabase database = TodoDatabase.getInstance(context);
        if(database.execSQL(sqlSave)==true) inputToDo.setText("");
        else {
            Toast toast = Toast.makeText(this.getContext(), "", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        final Context context = getView().getContext();
////        //mainFragment = new TodoFragment();
////
////        //getSupportFragmentManager 을 이용하여 이전에 만들었던 **FrameLayout**에 `fragment_main.xml`이 추가
//////        FragmentActivity activity = null;
//////        activity.getSupportFragmentManager().beginTransaction().replace(R.id.todoframelayout, mainFragment).commit();
////
//        View v = inflater.inflate(R.layout.fragment_todo, container, false);
//        Button saveButton = findViewById(R.id.savebutton);
//
//        //openDatabase(context);
//        return v;
//    }
//
//    private void saveToDo(Context context){
//        View view;
//        inputToDo = getView().findViewById(R.id.inputtodo);
//
//        //EditText에 적힌 글을 가져오기
//        String todo = inputToDo.getText().toString();
//
//        //테이블에 값을 추가하는 sql구문 insert...
//        String sqlSave = "insert into " + TodoDatabase.TABLE_NOTE + " (TODO) values (" +
//                "'" + todo + "')";
//
//        //sql문 실행
//        TodoDatabase database = TodoDatabase.getInstance(context);
//        if(database.execSQL(sqlSave)==true) inputToDo.setText("");
//        else {
//            Toast toast = Toast.makeText(this.getContext(), "", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//
//    public void openDatabase(Context context) {
//        // open database
//        if (TodoDatabase != null) {
//            TodoDatabase.close();
//            TodoDatabase = null;
//        }
//        TodoDatabase = TodoDatabase.getInstance(context);
//        boolean isOpen = TodoDatabase.open();
//        if (isOpen) {
//            Log.d(TAG, "Note database is open.");
//        } else {
//            Log.d(TAG, "Note database is not open.");
//        }
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        if (TodoDatabase != null) {
//            TodoDatabase.close();
//            TodoDatabase = null;
//        }
//    }

}