package com.example.nasaapod.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.nasaapod.listeners.ApiCallsInterface;
import com.example.nasaapod.listeners.ApiFailureListener;
import com.example.nasaapod.model.ApodResponse;

import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ApodResponse> getApodResponse;
    private Context context;
    private HomeModel homeModel;
    private static final String TAG = "HomeViewModel";
    public ApiFailureListener apiFailureListener;

    public HomeViewModel() {
        homeModel = new HomeModel();
    }

    public MutableLiveData<ApodResponse> apodResponse(String date) {
        getApodResponse = new MutableLiveData<>();
        homeModel.getResponse(date,new ApiCallsInterface() {
            @Override
            public void getApodResponse(Response<ApodResponse> ApodResponseCallback) {
                Log.i(TAG, "getApodResponse: "+ ApodResponseCallback.body().toString());
                getApodResponse.postValue(ApodResponseCallback.body());
            }

            @Override
            public void apiFailureResponse(String message) {
                apiFailureListener.apiFailureResponse(message);
            }
        });
        return getApodResponse;
    }

}
