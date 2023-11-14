package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateMemberCountBinding;

import java.util.ArrayList;

public class CreateMemberCountActivity extends AppCompatActivity {

    private ActivityCreateMemberCountBinding binding;
    private Intent intent;

    private int count;

    private NumberPicker numberPicker;

    private ArrayList<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateMemberCountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        Semina semina = (Semina) intent.getSerializableExtra("semina");
        count=0;

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });
        binding.activityCreateMemberCountNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(binding.createMemberCountPicker.getText().toString());
                if(count==0){
                    Toast.makeText(getApplicationContext(),"모집인원을 설정해주세요",Toast.LENGTH_LONG).show();
                }else{
                    Log.d("CreateMemberCountActivity",String.valueOf(count));
                    memberList = new ArrayList<>(count);
                    semina.setMemberList(memberList);
                    semina.setCapacity(count);
                    launcher.launch(new Intent(getApplicationContext(),CreateDescriptionActivity.class).putExtra("semina",semina)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            }
        });
    }
}