package com.example.themostusefulapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TodoFragment extends Fragment {

    private static final String TAG = "TodoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todo, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView){
    }
}
