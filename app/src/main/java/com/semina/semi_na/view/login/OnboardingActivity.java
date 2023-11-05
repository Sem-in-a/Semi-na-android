package com.semina.semi_na.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.semina.semi_na.R;
import com.semina.semi_na.base.MainActivity;

public class OnboardingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        Handler handler = new Handler();
        //sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //화면 전환
                startActivity(intent);
                finish();
//                if(!sharedPreferences.getString("studentNum","").equals("")){
//                    Log.d("OnboardingActivity",sharedPreferences.getString("studentNum",""));
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                    finish();
//                }
//                else{
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //화면 전환
//                    startActivity(intent);
//                    finish();
//                }
            }
        }, 1000);
    }
}