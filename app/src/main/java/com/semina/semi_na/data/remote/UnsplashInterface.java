package com.semina.semi_na.data.remote;

import com.semina.semi_na.data.remote.request.LoginRequest;
import com.semina.semi_na.data.remote.response.LoginResponse;
import com.semina.semi_na.data.remote.response.UnsplashResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UnsplashInterface {

    @GET("photos")
    Call<UnsplashResponse> getPhoto(
            @Query("page")int page,@Query("query")String query,@Query("client_id")String client_id);
}
