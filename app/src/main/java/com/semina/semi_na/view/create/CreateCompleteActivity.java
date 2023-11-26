package com.semina.semi_na.view.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.semina.semi_na.R;
import com.semina.semi_na.base.MainActivity;
import com.semina.semi_na.databinding.ActivityCreateCategoryBinding;
import com.semina.semi_na.databinding.ActivityCreateCompleteBinding;
import com.semina.semi_na.databinding.ActivityCreateMemberCountBinding;

public class CreateCompleteActivity extends AppCompatActivity {

    ActivityCreateCompleteBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.activityCreateCompleteNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}