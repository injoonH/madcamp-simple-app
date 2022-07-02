package com.example.themostusefulapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class fragment_todo extends Fragment {

    private static final String TAG = "fragment_todo";

    Fragment mainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainFragment = new TodoFragment();

        FragmentActivity activity=null;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.todoframelayout,mainFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_todo,container,false);
        Button saveButton = v.findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                saveToDo();

                Toast.makeText(getActivity(),"추가되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

    private void saveToDo() {
    }


}