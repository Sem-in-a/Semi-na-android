package com.semina.semi_na.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.semina.semi_na.R;

public class MainActivity extends AppCompatActivity {

    // 바텀 네비게이션
    BottomNavigationView bottomNavigationView;

    private String TAG = "메인";

    // 프래그먼트 변수
    private Fragment fragment_home;
    private Fragment fragment_create;
    private Fragment fragment_my;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int MENU_HOME = R.id.menu_home;
        final int MENU_CREATE_SEMINA = R.id.menu_create_semina;
        final int MENU_MYPAGE = R.id.menu_mypage;



        // 프래그먼트 생성
        fragment_home = new HomeFragment();
        fragment_create = new CreateFragment();
        fragment_my = new MyPageFragment();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case MENU_HOME:
                        Log.i(TAG, "home 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case MENU_CREATE_SEMINA:
                        Log.i(TAG, "세미나 등록 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_create).commitAllowingStateLoss();
                        return true;
                    case MENU_MYPAGE:
                        Log.i(TAG, "mypage 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_my).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });


    }
}