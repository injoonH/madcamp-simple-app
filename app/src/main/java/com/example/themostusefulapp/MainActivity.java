package com.example.themostusefulapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void init() {
        home_ly = findViewById(R.id.home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_phonenum: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home, new fragment_phone())
                            .commit();
                    return true;
                }
                case R.id.tab_image: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home, new fragment_image())
                            .commit();
                    return true;
                }
                case R.id.tab_todo: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home, new fragment_todo())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }
}