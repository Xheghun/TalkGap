package com.xheghun.vidit;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.xheghun.vidit.classes.ShowCamera;

public class VideoRecordActivity extends AppCompatActivity implements LifecycleOwner {


    private Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    private TextureView viewFinder;

    private final int REQUEST_CODE_PERMISSION = 10;
    private String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        frameLayout = findViewById(R.id.video_frame);





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {

        }
    }

    /**
     * Requesting permissions storage, audio and camera at once
     */




    private void updateTransform() {

    }
    private void startCamera() {
    }

    private void showSettingsDialog() {
    }
}
