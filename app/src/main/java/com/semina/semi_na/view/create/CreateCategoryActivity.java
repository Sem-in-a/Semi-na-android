package com.semina.semi_na.view.create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.ActivityCreateCategoryBinding;
import com.semina.semi_na.databinding.ActivityCreateTitleBinding;

public class CreateCategoryActivity extends AppCompatActivity {


    ActivityCreateCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}