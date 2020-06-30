package com.example.nasaapod.viewmodel;

import android.util.Log;

import com.example.nasaapod.constants.Constants;
import com.example.nasaapod.listeners.ApiCallsInterface;
import com.example.nasaapod.model.ApodResponse;
import com.example.nasaapod.network.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeModel {

    private static final String TAG = "HomeModel";

    public void getResponse(String date, final ApiCallsInterface apiCallsInterface) {

        ApiUtil.getApodResponse().getApodResponse(Constants.API_KEY, date).enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                Log.i(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    apiCallsInterface.getApodResponse(response);
                } else {
                    apiCallsInterface.apiFailureResponse(response.message() + " " + "Please select the date correctly!");
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                apiCallsInterface.apiFailureResponse(t.getMessage());
            }
        });
    }

}
