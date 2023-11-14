package com.semina.semi_na.view.create;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semina.semi_na.BuildConfig;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.remote.UnsplashClient;
import com.semina.semi_na.data.remote.UnsplashInterface;
import com.semina.semi_na.data.remote.response.UnsplashResponse;
import com.semina.semi_na.databinding.ActivityCreateThumbnailBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateThumbnailActivity extends AppCompatActivity {
    private ActivityCreateThumbnailBinding binding;

    private String message;
    private Intent intent;

    private FirebaseStorage firebaseStorage;

    private StorageReference storageReference;

    private Semina semina;

    private UnsplashInterface unsplashInterface;

    ActivityResultLauncher<Intent> launcher;

    private String unsplashKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateThumbnailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        unsplashInterface = UnsplashClient.getRetrofit().create(UnsplashInterface.class);
        unsplashKey = BuildConfig.UNSPLASH_KEY;
        intent = getIntent();
        semina = (Semina) intent.getSerializableExtra("semina");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });

        binding.activityCreateThumbNailNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = binding.createThumbnailKeywordEditText.getText().toString();
                if(message.length()<=8){
                    showToast("글자 수를 8자 이상 입력해 주세요");
                }else{

                    Log.d("CreateThumbnailActivity",message);
                    searchImgInUnsplash(message,unsplashKey);
                }

            }
        });

    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void searchImgInUnsplash(String query,String unsplashKey){

        Log.d("CreateThumbnailActivity","retrofit통신준비");
        Log.d("CreateThumbnailActivity",query);
        unsplashInterface.getPhoto(1,query,unsplashKey).enqueue(new Callback<UnsplashResponse>() {
            @Override
            public void onResponse(Call<UnsplashResponse> call, Response<UnsplashResponse> response) {

                if (response.isSuccessful()) {
                    ArrayList<UnsplashResponse.ResultData> result = response.body().getResults();
                    if (result != null && !result.isEmpty()) {
                        Log.d("CreateThumbnailActivity1", result.get(0).getUrls().getRegular());
                        String url = result.get(0).getUrls().getRegular();
                        loadImgInFireStore(semina.getTitle(),url);
                    } else {
                        Log.e("CreateThumbnailActivity2", String.valueOf(call.request().url()));
                    }
                }

            }

            @Override
            public void onFailure(Call<UnsplashResponse> call, Throwable t) {

                Log.e("CreateThumbnailActivity3", t.getMessage().toString());

            }
        });
    }

    // 이미지 로드 및 Firebase Storage에 업로드
    private void loadImgInFireStore(String title, String url) {
        Log.d("CreateThumbnailActivity4", "URL: " + url);
        StorageReference thumbRef = storageReference.child(title + ".jpg");

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(200,200)
                .fitCenter();

        Glide.with(this)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // 비트맵을 Firebase Storage에 업로드
                        uploadBitmapToFirebaseStorage(thumbRef, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });
    }

    // 비트맵을 Firebase Storage에 업로드
    private void uploadBitmapToFirebaseStorage(StorageReference thumbRef, Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = thumbRef.putBytes(data);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                thumbRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    Log.d("CreateThumbnailActivity5", "Image URL: " + imageUrl);

                    // 이미지 URL을 Semina 객체에 설정
                    semina.setImgUrl(imageUrl);

                    // 이후 작업 수행 (예: 액티비티 전환 등)
                    launchNextActivity();
                });
            });
        }
    }

    // 다음 액티비티를 시작하는 메서드
    private void launchNextActivity() {
        launcher.launch(new Intent(getApplicationContext(), ShowThumbNailImgActivity.class)
                .putExtra("semina", semina)
                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
    }






}