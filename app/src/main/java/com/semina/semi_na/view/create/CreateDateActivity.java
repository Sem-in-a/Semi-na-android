package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateDateBinding;

import java.util.Calendar;
import java.util.Date;

public class CreateDateActivity extends AppCompatActivity {

    private ActivityCreateDateBinding binding;
    private AlertDialog CustomTimePickerDialog;

    private int year;
    private int month;
    private int day;

    private String finalDate;
    private String finalTime;
    private Intent intent;

    private final String DATE = "";
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        finalDate = DATE;
        finalTime = DATE;

        intent = getIntent();
        Semina semina = (Semina) intent.getSerializableExtra("semina");

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });

        binding.createDateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                // Month만 주의 하면 됨 -> 0부터 11까지 리턴함
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            // onDateSet을 익명으로 만듦 -> 현재 선택된 year,month,day
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {

                                year = year;
                                month = monthOfYear+1;
                                dayOfMonth = dayOfMonth;
                                finalDate = String.valueOf(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일");
                                binding.createDateDate.setText(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일");

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }

        });

        binding.activityCreateDateNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finalTime = String.valueOf(binding.createDateTime.getText());
                if(finalDate.equals(DATE)){
                    showToast("날짜를 입력 해주세요");
                }else if(finalTime.equals(DATE)) {
                    showToast("시간을 입력 해주세요");
                }else{
                    semina.setDate(finalDate);
                    semina.setTime(finalTime);
                    launcher.launch(new Intent(getApplicationContext(),CreateMemberCountActivity.class).putExtra("semina",semina)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            }
        });

    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}