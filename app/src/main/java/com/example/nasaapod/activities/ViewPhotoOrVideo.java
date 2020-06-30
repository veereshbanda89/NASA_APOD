package com.example.nasaapod.activities;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nasaapod.R;

public class ViewPhotoOrVideo extends AppCompatActivity {

    private ImageView imageView;
    private ImageView arrowBack;
    private VideoView videoView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_video);

        arrowBack = findViewById(R.id.arrowBack);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);

        Bundle bundle = getIntent().getExtras();
        String mediaType = bundle.getString("MEDIA_TYPE");
        String displayImage = bundle.getString("PHOTO");
        String videoUrl = bundle.getString("VIDEO");

        if (mediaType.equals("image")) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(displayImage).into(imageView);
        } else {
            videoView.setVisibility(View.VISIBLE);
            showVideo(videoUrl);
        }

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showVideo(String videoUrl) {

        pd = new ProgressDialog(ViewPhotoOrVideo.this);
        pd.setMessage(getResources().getString(R.string.buffering_video));
        pd.show();

        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pd.dismiss();
            }
        });
    }
}
