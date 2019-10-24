package com.xheghun.vidit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoEditActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    MediaController mediaController;
    private Intent intent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);
        ButterKnife.bind(this);

        intent = getIntent();
        playVideo();

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

    }

    private void playVideo() {
        String videoPath = intent.getStringExtra("video_path");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        videoView.setLayoutParams(new LinearLayout.LayoutParams(width,height/2));
        mediaController = new MediaController(videoView.getContext());
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
