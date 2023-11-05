package com.semina.semi_na.view.create;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semina.semi_na.BuildConfig;
import com.semina.semi_na.R;
import com.theokanning.openai.service.OpenAiService;

import okhttp3.OkHttpClient;

public class CreateThumbnailActivity extends AppCompatActivity {
    private ObjectMapper mapper;
    private OkHttpClient client;

    private OpenAiService openAiService;

    private String OpenAIKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thumbnail);

        OpenAIKey = BuildConfig.OPENAI_KEY;
        Log.d("OpenAIActivity",OpenAIKey);
        openAiService = new OpenAiService("");


    }
}