package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.MajorCategory;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateHobbyBinding;
import com.semina.semi_na.databinding.ActivityCreateMajorBinding;

public class CreateMajorActivity extends AppCompatActivity {

    private ActivityCreateMajorBinding binding;
    private Intent intent;

    private MajorCategory majorCategory = MajorCategory.NULL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateMajorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });


        binding.createMajorIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorIt.setSelected(!view.isSelected());
                if (binding.createMajorIt.isSelected()) {
                    majorCategory = MajorCategory.IT;
                    binding.createMajorIt.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorIt.setBackground(null);
                }
            }
        });
        binding.createMajorEngineer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorEngineer.setSelected(!view.isSelected());
                if (binding.createMajorEngineer.isSelected()) {
                    majorCategory = MajorCategory.ENGINEERING;
                    binding.createMajorEngineer.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorEngineer.setBackground(null);
                }
            }
        });

        binding.createMajorBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorBusiness.setSelected(!view.isSelected());
                if (binding.createMajorBusiness.isSelected()) {
                    majorCategory = MajorCategory.BUSINESS;
                    binding.createMajorBusiness.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorBusiness.setBackground(null);
                }
            }
        });

        binding.createMajorEconomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorEconomic.setSelected(!view.isSelected());
                if (binding.createMajorEconomic.isSelected()) {
                    majorCategory = MajorCategory.ENGINEERING;
                    binding.createMajorEconomic.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorEconomic.setBackground(null);
                }
            }
        });
        binding.createMajorLaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorLaw.setSelected(!view.isSelected());
                if (binding.createMajorLaw.isSelected()) {
                    majorCategory = MajorCategory.LAW;
                    binding.createMajorLaw.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorLaw.setBackground(null);
                }
            }
        });
        binding.createMajorHumanity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorHumanity.setSelected(!view.isSelected());
                if (binding.createMajorHumanity.isSelected()) {
                    majorCategory = MajorCategory.HUMANITY;
                    binding.createMajorHumanity.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorHumanity.setBackground(null);
                }
            }
        });
        binding.createMajorScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorScience.setSelected(!view.isSelected());
                if (binding.createMajorScience.isSelected()) {
                    majorCategory = MajorCategory.SCIENCE;
                    binding.createMajorScience.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorScience.setBackground(null);
                }
            }
        });
        binding.createMajorSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createMajorSocial.setSelected(!view.isSelected());
                if (binding.createMajorSocial.isSelected()) {
                    majorCategory = MajorCategory.SOCIAL;
                    binding.createMajorSocial.setBackground(getDrawable(R.drawable.tab_selected_background));
                } else {
                    binding.createMajorSocial.setBackground(null);
                }
            }
        });

        binding.activityCreateMajorNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = getIntent();
                Semina semina = (Semina) intent.getSerializableExtra("semina");
                if (majorCategory == MajorCategory.NULL) {
                    showToast("전공을 선택해주세요");
                } else {
                    semina.setMajorCategory(majorCategory);
                    launcher.launch(new Intent(getApplicationContext(), CreateLocationActivity.class).putExtra("semina", semina)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            }
        });

    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    // 어떤 이넘 카테고리가 press됐는지에 따라 -> background stroke 정해준다.


}