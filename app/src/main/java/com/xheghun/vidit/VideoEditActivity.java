package com.xheghun.vidit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appyvet.materialrangebar.RangeBar;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xheghun.vidit.classes.Animate;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoEditActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    VideoView videoView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.cut_tools)
    BottomNavigationView bottom_nav;

    @BindView(R.id.range_bar)
    RangeBar rangeBar;

    private Intent intent;
    private FFmpeg ffmpeg;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);
        ButterKnife.bind(this);

        MediaController mediaController = new MediaController(this);
        ffmpeg = FFmpeg.getInstance(this);

        intent = getIntent();
        videoPath = intent.getStringExtra("video_path");
        playVideo();

        toolbar.setNavigationOnClickListener(v -> {
            ffmpeg.killRunningProcesses();
            onBackPressed();
            finish();
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (ffmpeg.isFFmpegCommandRunning()) {
                Toast.makeText(this, "process running", Toast.LENGTH_SHORT).show();
            } else  {
                onBackPressed();
                finish();
            }
            return true;
        });

        String newFileName =  System.currentTimeMillis() + ".mp4";
        File dest = new File(getFilesDir(),newFileName);

        bottom_nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.crop_vid:
                    Toast.makeText(VideoEditActivity.this, "Cropping...", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cut_vid:
                    Toast.makeText(VideoEditActivity.this, "Cutting...", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rotate_vid:
                    String[] rotateCMD = {"-i", videoPath,"-vf","transpose=1","-c:a","copy",dest.getAbsolutePath()};
                    try {
                        ffmpeg.execute(rotateCMD,new ExecuteBinaryResponseHandler() {
                            @Override
                            public void onSuccess(String message) {
                                super.onSuccess(message);
                                Animate.fadeOutAnimation(progressBar,VideoEditActivity.this);
                                Toast.makeText(VideoEditActivity.this, "done!", Toast.LENGTH_SHORT).show();
                                Uri newFile = Uri.parse(dest.getAbsolutePath());
                                videoView.setVideoURI(newFile);
                                videoView.start();
                                videoPath = dest.getAbsolutePath();
                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                                //textView.setText(message);
                                Animate.fadeOutAnimation(progressBar,VideoEditActivity.this);
                                Toast.makeText(VideoEditActivity.this, message, Toast.LENGTH_LONG).show();
                                Log.v("FFMPEG: ",message);

                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                Animate.fadeInAnimation(progressBar,VideoEditActivity.this);
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                Animate.fadeOutAnimation(progressBar,VideoEditActivity.this);
                               // Toast.makeText(VideoEditActivity.this, "finish", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (FFmpegCommandAlreadyRunningException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(VideoEditActivity.this, "Rotating...", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

    }

    private void playVideo() {
        String videoPath = intent.getStringExtra("video_path");

      /*  DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        videoView.setLayoutParams(new RelativeLayout.LayoutParams(width,height/2));*/
        MediaController mediaController = new MediaController(videoView.getContext());
        Uri uri = Uri.parse(videoPath);
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(uri);
        videoView.start();

        float tickEnd = (float) videoView.getDuration();


        rangeBar.setTickInterval(0.5f);
        rangeBar.setTickStart(1);
        rangeBar.setTickEnd(tickEnd);
    }
    private void loadFFMpegBinaries() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    super.onFailure();
                    Toast.makeText(VideoEditActivity.this, "FFmpeg binaries failed to load", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                    Toast.makeText(VideoEditActivity.this, "FFmpeg Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    Toast.makeText(VideoEditActivity.this, "loading FFmpeg started...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    Toast.makeText(VideoEditActivity.this, "finished loading.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
