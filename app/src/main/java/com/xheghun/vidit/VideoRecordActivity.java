package com.xheghun.vidit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.FrameLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
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


        Dexter.withActivity(getParent())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //open camera
                        camera = Camera.open();
                        showCamera = new ShowCamera(getApplicationContext(), camera);
                        frameLayout.addView(showCamera);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();


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
