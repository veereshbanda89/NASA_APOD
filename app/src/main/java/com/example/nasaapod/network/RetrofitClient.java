package com.example.nasaapod.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    static Retrofit getClient(String baseUrl) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(baseUrl)//passing API_URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())//passing OkHttpClient object
                .build();

        return restAdapter;
    }
}
