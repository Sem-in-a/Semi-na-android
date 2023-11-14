package com.semina.semi_na.data.remote;

import com.semina.semi_na.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashClient {

    private static final String BASE_URL = "https://api.unsplash.com/search/";

    private static Retrofit retrofit;


    public static Retrofit getRetrofit() {

        if(retrofit==null){
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.build();
        }
        return retrofit;
    }
}
