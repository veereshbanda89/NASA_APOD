package com.example.nasaapod.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.nasaapod.R;
import com.example.nasaapod.constants.Util;
import com.example.nasaapod.listeners.ApiFailureListener;
import com.example.nasaapod.model.ApodResponse;
import com.example.nasaapod.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements ApiFailureListener {

    private HomeViewModel viewModel;
    private ProgressBar progressBar;
    private DatePicker datePicker;
    private ImageView calendar;
    private TextView title;
    private ImageView imageView;
    private ImageView videoView;
    private TextView description;
    private ImageView zoomPhoto;
    private ImageView playVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.title);
        calendar = findViewById(R.id.calendar);
        datePicker = findViewById(R.id.datePicker);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        description = findViewById(R.id.description);
        zoomPhoto = findViewById(R.id.zoomPhoto);
        playVideo = findViewById(R.id.playVideo);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.apiFailureListener = this;

        callApi("");

        calendar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);
                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        datePicker.setVisibility(View.GONE);
                        callApi(getDateChanged());
                    }
                });
            }
        });

    }

    private void callApi(String date) {
        if (!Util.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        } else {
            callApodApi(date);
        }
    }

    private void callApodApi(String date) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.apodResponse(date).observe(this, new Observer<ApodResponse>() {
            @Override
            public void onChanged(ApodResponse apodResponse) {
                progressBar.setVisibility(View.GONE);
                if (apodResponse != null) {
                    setApodDetails(apodResponse);
                }
            }
        });
    }

    private void setApodDetails(ApodResponse apodResponse) {
        title.setText(apodResponse.getTitle());
        description.setText(apodResponse.getExplanation());
        if(apodResponse.getMediaType().equals("image")){
            zoomPhoto.setVisibility(View.VISIBLE);
            playVideo.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(apodResponse.getHdurl()).into(imageView);
            zoomPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ViewPhotoOrVideo.class);
                    intent.putExtra("PHOTO", apodResponse.getHdurl());
                    intent.putExtra("MEDIA_TYPE",apodResponse.getMediaType());
                    startActivity(intent);
                }
            });
        }else{
            playVideo.setVisibility(View.VISIBLE);
            zoomPhoto.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ViewPhotoOrVideo.class);
                    intent.putExtra("VIDEO", apodResponse.getUrl());
                    intent.putExtra("MEDIA_TYPE",apodResponse.getMediaType());
                    startActivity(intent);
                }
            });
        }
    }

    public String getDateChanged(){

        StringBuilder builder = new StringBuilder();
        builder.append(datePicker.getYear()+"-");
        builder.append((datePicker.getMonth()+1)+"-");
        builder.append(datePicker.getDayOfMonth());

        return builder.toString();
    }

    @Override
    public void apiFailureResponse(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
