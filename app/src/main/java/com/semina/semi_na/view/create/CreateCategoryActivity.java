package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.db.entity.SeminaCategory;
import com.semina.semi_na.databinding.ActivityCreateCategoryBinding;
import com.semina.semi_na.databinding.ActivityCreateTitleBinding;

public class CreateCategoryActivity extends AppCompatActivity {

    private ActivityCreateCategoryBinding binding;
    private Intent intent;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode()==RESULT_OK){

                        }

                    }
                });


        binding.createCategoryMajorBtn.setOnClickListener(view->{
            binding.createCategoryMajorBtn.setBackground(getResources().getDrawable(R.drawable.button_select_stroke));
            binding.createCategoryHobbyBtn.setBackground(getDrawable(R.color.white));
            binding.createCategoryMajorBtn.setFocusable(true);
            binding.createCategoryHobbyBtn.setFocusable(false);
        });

        binding.createCategoryHobbyBtn.setOnClickListener(view->{
            view.setBackground(getResources().getDrawable(R.drawable.button_select_stroke));
            binding.createCategoryMajorBtn.setBackground(getDrawable(R.color.white));
            binding.createCategoryMajorBtn.setFocusable(View.NOT_FOCUSABLE);
            binding.createCategoryHobbyBtn.setFocusable(View.FOCUSABLE);
        });

        binding.activityCreateCategoryNextBtn.setOnClickListener(view->{
            intent = getIntent();
            Semina semina = (Semina) intent.getSerializableExtra("semina");
            if(binding.createCategoryHobbyBtn.getFocusable()==View.FOCUSABLE){
                semina.setSeminaCategory(SeminaCategory.HOBBY);
                launcher.launch(new Intent(getApplicationContext(),CreateHobbyActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }else if(binding.createCategoryMajorBtn.getFocusable()==View.FOCUSABLE){
                semina.setSeminaCategory(SeminaCategory.MAJOR);
                launcher.launch(new Intent(getApplicationContext(),CreateMajorActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }else if(binding.createCategoryHobbyBtn.getFocusable()==View.NOT_FOCUSABLE && binding.createCategoryMajorBtn.getFocusable()==View.NOT_FOCUSABLE){
                showToast("카테고리를 선택해주세요");
            }



        });
    }

    public void showToast(String msg){

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

    }
}