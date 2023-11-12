package com.semina.semi_na.data.remote;

import com.semina.semi_na.BuildConfig;
import com.semina.semi_na.data.remote.request.OpenAIRequest;
import com.semina.semi_na.data.remote.response.LoginResponse;
import com.semina.semi_na.data.remote.response.OpenAIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIInterface {

    String token = "Bearer "+ BuildConfig.OPENAI_KEY;
    @Headers("Authorization:"+token)
    @POST("generations")
    Call<OpenAIResponse> postImgGenerate(
            @Body OpenAIRequest openAIRequest);
}
