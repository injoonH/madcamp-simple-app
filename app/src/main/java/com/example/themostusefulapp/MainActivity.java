package com.example.themostusefulapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.themostusefulapp.ThreeMenuFragment.FragmentContacts;
import com.example.themostusefulapp.ThreeMenuFragment.FragmentGallery;
import com.example.themostusefulapp.ThreeMenuFragment.FragmentTodo;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ==== set initial fragment ==== */

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.tabContainer, FragmentContacts.class, null)
                    .commit();
        }

        /* ==== set navigation bar ==== */

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tab_phonenum)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, FragmentContacts.class, null)
                        .commit();
            else if (itemId == R.id.tab_image)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, FragmentGallery.class, null)
                        .commit();
            else if (itemId == R.id.tab_todo)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, FragmentTodo.class, null)
                        .commit();
            else
                return false;
            return true;
        });
        navigation.setOnItemReselectedListener(null);
    }
}