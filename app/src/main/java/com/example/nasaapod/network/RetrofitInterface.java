package com.example.nasaapod.network;


import com.example.nasaapod.model.ApodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/planetary/apod")
    Call<ApodResponse> getApodResponse(@Query("api_key") String apiKey,@Query("date") String date);
}