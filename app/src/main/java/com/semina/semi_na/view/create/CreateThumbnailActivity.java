package com.semina.semi_na.view.create;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semina.semi_na.BuildConfig;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.remote.OpenAIClient;
import com.semina.semi_na.data.remote.OpenAIInterface;
import com.semina.semi_na.databinding.ActivityCreateThumbnailBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CreateThumbnailActivity extends AppCompatActivity {
    private ActivityCreateThumbnailBinding binding;
    private String OpenAIKey;

    private OpenAIInterface openAIInterface;

    private String message;
    Intent intent;

    private FirebaseStorage firebaseStorage;

    private StorageReference storageReference;

    private Semina semina;

    ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateThumbnailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OpenAIKey = BuildConfig.OPENAI_KEY;
        String token = "Bearer "+OpenAIKey;
        Log.d("OpenAIActivity",OpenAIKey);
        openAIInterface = OpenAIClient.getRetrofit().create(OpenAIInterface.class);

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
                message = binding.createThumbnailPrompt.getText().toString();
                if(message.length()<=20){
                    showToast("글자 수를 20자 이상 입력해 주세요");
                }else{

                    Log.d("CreateThumbnailActivity",message);
                    semina.setDescription(message);
                    loadImgInFireStore(semina.getTitle(),"https://images.unsplash.com/photo-1682685797886-79020b7462a4?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");


//                    OpenAIRequest openAIRequest = new OpenAIRequest("dall-e-3",message,1,"1024x1024");
//                    openAIInterface.postImgGenerate(openAIRequest).enqueue(new Callback<OpenAIResponse>() {
//                        @Override
//                        public void onResponse(Call<OpenAIResponse> call, Response<OpenAIResponse> response) {
//                            if(response.isSuccessful()){
//
//                                Log.d("CreateThumbnailActivity",response.body().toString());
//                                OpenAIResponse openAIResponse = response.body();
//                                assert openAIResponse!=null;
//
//                                Log.d("CreateThumbnailActivity",openAIResponse.getPromptData().get(0).getUrl());
//
//                                String url = openAIResponse.getPromptData().get(0).getUrl();
//                                loadImgInFireStore(semina.getTitle(),url);
//
//
//
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<OpenAIResponse> call, Throwable t) {
//
//                        }
//                    });

                }

            }
        });




    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void loadImgInFireStore(String title,String url){

        Log.d("CreateThumbnailActivity", url);
        StorageReference thumbRef = storageReference.child(title + ".jpg");

        ImageDownloadListener listener = imageUrl -> {
            semina.setImgUrl(imageUrl);
            Log.d("CreateThumbnailActivity", imageUrl);
            launcher.launch(new Intent(CreateThumbnailActivity.this, ShowThumbNailImgActivity.class).putExtra("semina", semina)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        };

        ImageDownloadTask imageDownloadTask = new ImageDownloadTask(thumbRef, listener);
        imageDownloadTask.execute(url);


    }

    public interface ImageDownloadListener {
        void onImageDownloaded(String imageUrl);
    }


    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private final StorageReference thumbRef;
        private final ImageDownloadListener listener;

        private String url;

        public ImageDownloadTask(StorageReference thumbRef, ImageDownloadListener listener) {
            this.thumbRef = thumbRef;
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            return getBitmapFromURL(url);
        }

        public String getUrl() {
            return url;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = thumbRef.putBytes(data);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    thumbRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        url = uri.toString();
                        if (listener != null) {
                            listener.onImageDownloaded(url);
                        }
                    });
                });
            }
        }
    }


}