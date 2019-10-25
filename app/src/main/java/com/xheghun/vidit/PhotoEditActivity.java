package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xheghun.vidit.adapter.EditPhotoRecyclerAdapter;
import com.xheghun.vidit.adapter.EffectsRecyclerAdapter;
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


    @BindView(R.id.effects_recycler_view)
    RecyclerView effectsRecycler;

    List<GalleryMedia> imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String[] images = intent.getStringArrayExtra("images");
        imagePath = new ArrayList<>();
        if (images != null) {
            setupImages(images);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        effectsRecycler.setAdapter(new EffectsRecyclerAdapter(this));
        effectsRecycler.setLayoutManager(layoutManager);
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
