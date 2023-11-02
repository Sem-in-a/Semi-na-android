package com.semina.semi_na.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.semina.semi_na.R;
import com.semina.semi_na.data.remote.RetrofitClient;
import com.semina.semi_na.data.remote.RetrofitInterface;
import com.semina.semi_na.data.remote.request.LoginRequest;
import com.semina.semi_na.data.remote.response.LoginResponse;
import com.semina.semi_na.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private EditText studentNumField;
    private String studentNum;
    private EditText passwordField;

    private Button loginBtn;
    private String password;

    private RetrofitInterface retrofitInterface;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentNumField = findViewById(R.id.login_studentNum_editText);
        passwordField = binding.loginPasswordEditText;
        loginBtn = binding.loginBtn;
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);



        loginBtn.setOnClickListener(view->{
            studentNum = String.valueOf(studentNumField.getText());
            password = String.valueOf(passwordField.getText());
            Log.d("studentNum",studentNum);
            Log.d("kathy",password);
            if(studentNum.equals("")){
                showToast("학번을 입력해주세요");
            }else if(password.equals("")){
                showToast("비밀번호를 입력해주세요");
            }else{
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setStudentNum(studentNum);
                loginRequest.setPassword(password);
               retrofitInterface.postLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
                   @Override
                   public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                       if(response.isSuccessful()) {
                          LoginResponse loginResponse =response.body();
                           assert loginResponse != null;
                           Log.d("loginres",loginResponse.getDept());

                           /*
                           @TODO Shared Preferences에 학번 이름 학부 토큰 저장
                           @TODO 파베 연결 해두기

                            */
                           //Editor를 preferences에 쓰겠다고 연결
                           SharedPreferences.Editor editor = preferences.edit();
//                           //putString(KEY,VALUE)
//                           editor.putString("userid",edt_id.getText().toString());
//                           editor.putString("userpwd",edt_pwd.getText().toString());
//                           //항상 commit & apply 를 해주어야 저장이 된다.
//                           editor.commit();
//                           //메소드 호출
//                           getPreferences();

                       }

                   }

                   @Override
                   public void onFailure(Call<LoginResponse> call, Throwable t) {

                   }
               });

            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}