package com.xheghun.vidit;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.xheghun.vidit.adapter.TabAdapter;
import com.xheghun.vidit.fragments.GalleryImageFragment;
import com.xheghun.vidit.fragments.GalleryVideoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {


    private static final int REQUEST_PERMISSION_KEY = 100;
    @BindView(R.id.gallery_viewpager)
    ViewPager pager;
    @BindView(R.id.gallery_tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
       checkForPermission();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void checkForPermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), 0);
                            adapter.addFragment(new GalleryImageFragment(), "Images");
                            adapter.addFragment(new GalleryVideoFragment(), "Videos");
                            pager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(pager);
                        } else {
                            Toast.makeText(getApplicationContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}