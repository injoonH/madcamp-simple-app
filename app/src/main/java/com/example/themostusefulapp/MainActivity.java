package com.example.themostusefulapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ==== set navigation bar ==== */

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        FragmentManager fragmentManager = getSupportFragmentManager();

        navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tab_phonenum)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, FragmentPhone.class, null)
                        .commit();
            else if (itemId == R.id.tab_image)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, fragment_image.class, null)
                        .commit();
            else if (itemId == R.id.tab_todo)
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.tabContainer, fragment_todo.class, null)
                        .commit();
            else
                return false;
            return true;
        });
        navigation.setOnItemReselectedListener(null);
    }
}