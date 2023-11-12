package com.semina.semi_na.view.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.ActivityCreateDateBinding;

import java.util.Calendar;
import java.util.Date;

public class CreateDateActivity extends AppCompatActivity {

    private ActivityCreateDateBinding binding;
    private AlertDialog CustomTimePickerDialog;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        year=0;


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

                                binding.createDateDate.setText(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일");

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }

        });

        Log.d("CreateDateActivity",year+" "+month+" "+day);
    }
}