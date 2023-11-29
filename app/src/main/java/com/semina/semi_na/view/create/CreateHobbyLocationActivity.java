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
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateHobbyLocationBinding;

public class CreateHobbyLocationActivity extends AppCompatActivity {

    ActivityCreateHobbyLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateHobbyLocationBinding.inflate(getLayoutInflater());

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });
        binding.activityCreateHobbyLocationNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = String.valueOf(binding.createLocationHobbyTextview.getText());
                Semina semina = (Semina) getIntent().getSerializableExtra("semina");
                if(data.equals("")){
                    showToast("만날 장소를 입력해주세요!");
                }else{
                    semina.setHobbyLocation(data);
                    launcher.launch(new Intent(getApplicationContext(),CreateDateActivity.class).putExtra("semina",semina));
                }

            }
        });
        setContentView(binding.getRoot());
    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}