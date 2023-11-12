package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.MajorCategory;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateHobbyBinding;

public class CreateHobbyActivity extends AppCompatActivity {

    private ActivityCreateHobbyBinding binding;
    private Intent intent;

    private HobbyCategory hobbyCategory = HobbyCategory.NULL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateHobbyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        Semina semina = (Semina) intent.getSerializableExtra("semina");
        semina.setMajorCategory(MajorCategory.NULL);


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });


        binding.createHobbyExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createHobbyExercise.setSelected(!view.isSelected());
                if(binding.createHobbyExercise.isSelected()){
                    hobbyCategory = HobbyCategory.EXERCISE;
                    binding.createHobbyExercise.setBackground(getDrawable(R.drawable.tab_selected_background));
                }else{
                    binding.createHobbyExercise.setBackground(null);
                }

            }
        });

        binding.createHobbyMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createHobbyMusic.setSelected(!view.isSelected());
                if(binding.createHobbyMusic.isSelected()){
                   hobbyCategory = HobbyCategory.MUSIC;
                    binding.createHobbyMusic.setBackground(getDrawable(R.drawable.tab_selected_background));
                }else{
                    binding.createHobbyMusic.setBackground(null);
                }

            }
        });

        binding.createHobbyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createHobbyBook.setSelected(!view.isSelected());
                if(binding.createHobbyBook.isSelected()){
                    hobbyCategory = HobbyCategory.BOOK;
                    binding.createHobbyBook.setBackground(getDrawable(R.drawable.tab_selected_background));
                }else{
                    binding.createHobbyBook.setBackground(null);
                }

            }
        });

        binding.createHobbyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createHobbyFood.setSelected(!view.isSelected());
                if(binding.createHobbyFood.isSelected()){
                    hobbyCategory = HobbyCategory.BOOK;
                    binding.createHobbyFood.setBackground(getDrawable(R.drawable.tab_selected_background));
                }else{
                    binding.createHobbyFood.setBackground(null);
                }
            }
        });

        binding.activityCreateHobbyNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hobbyCategory==HobbyCategory.NULL){
                    showToast("취미를 선택해주세요");
                }else{
                semina.setHobbyCategory(hobbyCategory);
                launcher.launch(new Intent(getApplicationContext(),CreateLocationActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
            }
        });

    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}