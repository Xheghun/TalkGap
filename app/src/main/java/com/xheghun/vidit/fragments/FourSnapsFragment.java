package com.xheghun.vidit.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.xheghun.vidit.GalleryActivity;
import com.xheghun.vidit.R;

import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourSnapsFragment extends Fragment {
    private CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSION = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    @BindView(R.id.texture_view)
    TextureView textureView;
    private View view;

    public FourSnapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_four_snaps, container, false);
        ButterKnife.bind(this, view);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSION, REQUEST_CODE_PERMISSIONS);
        }


        return view;
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSION) {
            if (ContextCompat.checkSelfPermission(getContext(),permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void startCamera() {
        CameraX.unbindAll();

        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());//size of the screen

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(output -> {
            ViewGroup parent = (ViewGroup) textureView.getParent();
            parent.removeView(textureView);
            parent.addView(textureView, 0);

            textureView.setSurfaceTexture(output.getSurfaceTexture());
            updateTransform();
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        view.findViewById(R.id.switch_cam_btn).setOnClickListener(v -> {
            if (CameraX.LensFacing.FRONT == lensFacing) {
                lensFacing = CameraX.LensFacing.BACK;
            }else {
                lensFacing = CameraX.LensFacing.BACK;
            }
            try {
                CameraX.getCameraWithLensFacing(lensFacing);
            }catch (Exception e) {

            }
        });

        view.findViewById(R.id.snap_btn).setOnClickListener(v -> {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png");
            imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                @Override
                public void onImageSaved(@NonNull File file) {
                    String msg = "Pic captured at " + file.getAbsolutePath();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                }

                @Override
                public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                    String msg = "Pic capture failed: " + message;
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    if (cause != null) {
                        cause.printStackTrace();
                    }
                }
            });
        });
        CameraX.bindToLifecycle(this, preview, imgCap);

    }

    private void updateTransform() {
        Matrix mx = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();
        float cX = w / 2f;
        float cY = h / 2f;

        int rotationDgr;
        int rotation = (int) textureView.getRotation();

        switch (rotation) {
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }
        mx.postRotate((float)rotationDgr,cX,cY);
        textureView.setTransform(mx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else  {
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.gallery_btn)
    void openGallery() {
        startActivity(new Intent(getContext(), GalleryActivity.class));
    }
}
