package com.semina.semi_na.view.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.ActivityCreateTitleBinding;

public class CreateTitleActivity extends AppCompatActivity {

    Button nextButton;
    EditText editTitle;
    String title;

    ActivityCreateTitleBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nextButton = binding.activityCreateTitleNextBtn;
        editTitle = binding.createTitleEdit;

        title = "";

        Log.d("CreateTitleActivity",title);


        nextButton.setOnClickListener(view->{
            title = String.valueOf(editTitle.getText());
            Log.d("CreateTitleActivity",title);

            if(title.equals("")){
                showToast("세미나 이름을 적어주세요");
            }else{
                startActivity(new Intent(getApplicationContext(), CreateCategoryActivity.class));
            }

        });
    }

    // @TODO ActivityForResult Register 어쩌구저쩌구 하기

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}