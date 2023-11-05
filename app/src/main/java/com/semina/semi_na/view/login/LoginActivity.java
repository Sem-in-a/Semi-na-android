package com.semina.semi_na.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.semina.semi_na.R;
import com.semina.semi_na.base.MainActivity;
import com.semina.semi_na.data.remote.RetrofitClient;
import com.semina.semi_na.data.remote.RetrofitInterface;
import com.semina.semi_na.data.remote.entity.Member;
import com.semina.semi_na.data.remote.request.LoginRequest;
import com.semina.semi_na.data.remote.response.LoginResponse;
import com.semina.semi_na.databinding.ActivityLoginBinding;

import org.w3c.dom.Document;

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

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentNumField = binding.loginStudentNumEditText;
        passwordField = binding.loginPasswordEditText;
        loginBtn = binding.loginBtn;
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        database = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(view -> {

            studentNum = String.valueOf(studentNumField.getText());
            password = String.valueOf(passwordField.getText());
            Log.d("LoginActivity", studentNum);
            Log.d("LoginActivity", password);
            if (studentNum.equals("")) {
                showToast("학번을 입력해주세요");
            } else if (password.equals("")) {
                showToast("비밀번호를 입력해주세요");
            } else {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setStudentNum(studentNum);
                loginRequest.setPassword(password);
                retrofitInterface.postLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            assert loginResponse != null;
                            Log.d("LoginActivity", loginResponse.getDept());

                            String department = loginResponse.getParentDept();
                            String major = loginResponse.getDept();
                            String name = loginResponse.getName();

                            database.collection("Member")
                                    .whereEqualTo("studentNum", studentNum)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            Log.d("LoginActivity", task.getResult().getDocuments().toString());
                                            Log.d("LoginActivity","이미 유저 있음");
                                            // 유저 정보가 있다는거
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("studentNum", studentNum);
                                            editor.putString("depart", department);
                                            editor.putString("name", name);
                                            editor.putString("major", major);
                                            //항상 commit & apply 를 해주어야 저장이 된다.
                                            editor.commit();
                                            editor.apply();
                                            Log.d("LoginActivity", preferences.getString("studentNum", ""));
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        }
                                    }).addOnCanceledListener(new OnCanceledListener() {
                                        @Override
                                        public void onCanceled() {
                                            Member member = new Member(studentNum, department, name, major);
                                            Log.d("LoginActivity", "파이어 스토어접근");
                                            database.collection("Member").add(member).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    Log.d("LoginActivity", "파이어스토어 저장");
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("studentNum", studentNum);
                                                    editor.putString("depart", department);
                                                    editor.putString("name", name);
                                                    editor.putString("major", major);
                                                    //항상 commit & apply 를 해주어야 저장이 된다.
                                                    editor.commit();
                                                    editor.apply();
                                                    Log.d("LoginActivity", preferences.getString("studentNum", ""));
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                }
                                            });


                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }

                });
            }
        });
    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}