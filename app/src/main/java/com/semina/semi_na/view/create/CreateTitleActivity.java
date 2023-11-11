package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.db.entity.Member;
import com.semina.semi_na.databinding.ActivityCreateTitleBinding;

public class CreateTitleActivity extends AppCompatActivity {

    private Button nextButton;
    private EditText editTitle;
    private String title;
    private SharedPreferences studentNum;
    private SharedPreferences name;
    private SharedPreferences department;
    private SharedPreferences major;
    ActivityCreateTitleBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nextButton = binding.activityCreateTitleNextBtn;
        editTitle = binding.createTitleEdit;
        title = "";
        studentNum = getSharedPreferences("studentNum", MODE_PRIVATE);
        name = getSharedPreferences("name",MODE_PRIVATE);
        department = getSharedPreferences("department",MODE_PRIVATE);
        major = getSharedPreferences("major",MODE_PRIVATE);

        Log.d("CreateTitleActivity",title);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });



        nextButton.setOnClickListener(view->{
            title = String.valueOf(editTitle.getText());
            Log.d("CreateTitleActivity",title);

            if(title.equals("")){
                showToast("세미나 이름을 적어주세요");
            }else{
                Semina semina = new Semina();
                Member member = new Member(studentNum.toString(), department.toString(), name.toString(), major.toString());
                semina.setHost(member);
                semina.setTitle(title);
                launcher.launch(new Intent(getApplicationContext(),CreateCategoryActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                //startActivityForResult(new Intent(getApplicationContext(), CreateCategoryActivity.class));
            }

        });
    }

    // @TODO ActivityForResult Register 어쩌구저쩌구 하기

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}