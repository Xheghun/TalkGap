package com.xheghun.vidit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xheghun.vidit.adapter.TabAdapter;
import com.xheghun.vidit.fragments.GalleryImageFragment;
import com.xheghun.vidit.fragments.GalleryVideoFragment;

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
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new GalleryImageFragment(), "Images");
        adapter.addFragment(new GalleryVideoFragment(), "Videos");
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}