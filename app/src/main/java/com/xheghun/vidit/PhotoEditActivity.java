package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xheghun.vidit.adapter.EditPhotoRecyclerAdapter;
import com.xheghun.vidit.adapter.EffectsRecyclerAdapter;
import com.xheghun.vidit.classes.Animate;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoEditActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView mImageView;

    @BindView(R.id.edit_image_rc)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottom_nav;

    @BindView(R.id.effects_rc)
    RecyclerView effects_rc;

    @BindView(R.id.theme_rc)
    RecyclerView theme_rc;

    @BindView(R.id.filter_recycler_view)
    RecyclerView filter_rc;

    List<GalleryMedia> imagePath;

    boolean isFilterLayoutVisible = false;
    boolean isEffectsVisible = false;
    boolean isThemeVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });


        String[] images = intent.getStringArrayExtra("images");
        imagePath = new ArrayList<>();
        if (images != null) {
            setupImages(images);
        }


        filter_rc.setAdapter(new EffectsRecyclerAdapter(this,"Filter"));
        filter_rc.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        effects_rc.setAdapter(new EffectsRecyclerAdapter(this, "Effects"));
        effects_rc.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        theme_rc.setAdapter(new EffectsRecyclerAdapter(this,"Theme"));
        theme_rc.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        bottom_nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.filter:
                    if (!isFilterLayoutVisible || (isEffectsVisible || isThemeVisible)) {
                        //hide unnecessary views
                        Animate.slideDown(theme_rc,this);
                        Animate.slideDown(effects_rc,this);
                        
                        //show filters
                        Animate.slideUp(filter_rc,this);
                        isFilterLayoutVisible = true;
                        isThemeVisible = false;
                        isEffectsVisible = false;
                    } else {
                        Animate.slideDown(filter_rc,this);
                        isFilterLayoutVisible = false;
                    }
                    break;
                case R.id.effects:
                    if (!isEffectsVisible || (isFilterLayoutVisible || isThemeVisible)) {
                        //hide unnecessary views
                        Animate.slideDown(theme_rc,this);
                        Animate.slideDown(filter_rc,this);

                        //show effects
                        Animate.slideUp(effects_rc,this);
                        isEffectsVisible = true;

                        isThemeVisible = false;
                        isFilterLayoutVisible = false;
                    }else {
                        Animate.slideDown(effects_rc,this);
                        isEffectsVisible = false;
                    }
                    break;
                case R.id.theme:
                    if (!isThemeVisible || (isFilterLayoutVisible || isEffectsVisible)) {
                        //hide unnecessary views
                        Animate.slideDown(effects_rc,this);
                        Animate.slideDown(filter_rc,this);

                        //show themes
                        Animate.slideUp(theme_rc,this);
                        isThemeVisible = true;

                        isEffectsVisible = false;
                        isFilterLayoutVisible = false;
                    }else {
                        Animate.slideDown(theme_rc,this);
                        isThemeVisible = false;
                    }
                    break;
                case R.id.add_music:
                    break;
            }
            return true;
        });

    }

    private void setupImages(String[] images) {
        //load first image
        Glide.with(this).load(images[0]).override(mImageView.getWidth(),mImageView.getHeight())
                .into(mImageView);


        for (String image : images) {
            GalleryMedia galleryMedia = new GalleryMedia();
            galleryMedia.setPath(image);
            imagePath.add(galleryMedia);
        }

        mRecyclerView.setAdapter(new EditPhotoRecyclerAdapter(this, imagePath, media -> {
            Glide.with(this).load(media.getPath()).override(mImageView.getWidth(),mImageView.getHeight())
                    .into(mImageView);
        }));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
