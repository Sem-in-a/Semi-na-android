package com.semina.semi_na.view.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.ActivityCreateHobbyBinding;

public class CreateHobbyActivity extends AppCompatActivity {

    private ActivityCreateHobbyBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateHobbyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = new Intent();



    }
}