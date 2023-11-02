package com.semina.semi_na.data.remote;

import com.semina.semi_na.data.remote.request.LoginRequest;
import com.semina.semi_na.data.remote.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("login")
    Call<LoginResponse> postLogin(
            @Body LoginRequest loginRequest);
}
