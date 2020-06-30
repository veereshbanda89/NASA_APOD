package com.example.nasaapod.listeners;


import com.example.nasaapod.model.ApodResponse;

import retrofit2.Response;

public interface ApiCallsInterface {

    public void getApodResponse(Response<ApodResponse> ApodResponseCallback);

    public void apiFailureResponse(String message);
}

