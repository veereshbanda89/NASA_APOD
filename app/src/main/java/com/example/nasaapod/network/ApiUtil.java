package com.example.nasaapod.network;

import com.example.nasaapod.constants.Constants;

public class ApiUtil {
    public static RetrofitInterface getApodResponse() {
        return RetrofitClient.getClient(Constants.HOME_PAGE_API).create(RetrofitInterface.class);
    }
}
