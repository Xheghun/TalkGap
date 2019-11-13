package com.xheghun.vidit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xheghun.vidit.Util.BitmapUtil;
import com.xheghun.vidit.adapter.EditPhotoRecyclerAdapter;
import com.xheghun.vidit.adapter.EffectsRecyclerAdapter;
import com.xheghun.vidit.adapter.FilterRecyclerAdapter;
import com.xheghun.vidit.classes.Animate;
import com.xheghun.vidit.classes.ThumbnailsManager;
import com.xheghun.vidit.models.ImageData;
import com.xheghun.vidit.models.MyFilters;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoEditActivity extends AppCompatActivity {

    private static final int MUSIC_REQUEST_CODE = 9092;
    @BindView(R.id.imageView)
    ImageView mImageView;

    @BindView(R.id.edit_image_rc)
    RecyclerView mRecyclerView;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottom_nav;

    @BindView(R.id.effects_rc)
    RecyclerView effects_rc;

    @BindView(R.id.back_btn)
    ImageButton back_btn;

    @BindView(R.id.theme_rc)
    RecyclerView theme_rc;

    @BindView(R.id.filter_recycler_view)
    RecyclerView filter_rc;

    List<ImageData> imagePath;
    List<MyFilters> filterList;

    boolean isFilterLayoutVisible = false;
    boolean isEffectsVisible = false;
    boolean isThemeVisible = false;


    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);
        ButterKnife.bind(this);

        back_btn.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();

        String[] images = intent.getStringArrayExtra("images");
        imagePath = new ArrayList<>();
        if (images != null) {
            setupImages(images);
        }


         filterList = new ArrayList<>();

        Bitmap bits = BitmapUtil.getBitmap(R.drawable.logo,this);
        String name = "Filter Name";
        Filter[] filters = {SampleFilters.getAweStruckVibeFilter(),SampleFilters.getBlueMessFilter(),
        SampleFilters.getLimeStutterFilter(),SampleFilters.getNightWhisperFilter(),SampleFilters.getStarLitFilter()};

        for (int i = 0; i < filters.length; i++) {
            MyFilters mFilters = new MyFilters();
            mFilters.setImage(bits);
            mFilters.setName(name);
            mFilters.setFilter(filters[i]);
            filterList.add(mFilters);
        }

        filter_rc.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        filter_rc.setHasFixedSize(true);
        FilterRecyclerAdapter adapter = new FilterRecyclerAdapter(filterList, filter -> {
            Bitmap newImg = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
            mImageView.setImageBitmap(filter.getFilter().processFilter(newImg));
        });
        filter_rc.setAdapter(adapter);

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
                        
                        //show filterList
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
                    startActivityForResult(new Intent(this,MusicSelectActivity.class),MUSIC_REQUEST_CODE);
                    break;
            }
            return true;
        });

    }

    private void setupImages(String[] images) {
        //load first image
        Bitmap bitmap = BitmapUtil.getBitmap(images[0]);

        Glide.with(this).load(bitmap).override(mImageView.getWidth(),mImageView.getHeight())
                .into(mImageView);

        for (String image : images) {
            ImageData path = new ImageData();
            Bitmap newImage = BitmapUtil.getBitmap(image);

            path.setImageBitmap(newImage);
            imagePath.add(path);
        }

        mRecyclerView.setAdapter(new EditPhotoRecyclerAdapter(this, imagePath, media -> Glide.with(this).load(media.getImageBitmap()).override(mImageView.getWidth(),mImageView.getHeight())
                .into(mImageView)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void bindToFilterAdapter() {

        Handler handler = new Handler();
        Runnable r = () -> {


            Bitmap thumbImage = Bitmap.createScaledBitmap(BitmapUtil.getBitmap(R.drawable.logo, PhotoEditActivity.this),640,640,false);
            MyFilters filter1 = new MyFilters();
            MyFilters filter2 = new MyFilters();
            MyFilters filter3 = new MyFilters();
            MyFilters filter4 = new MyFilters();
            MyFilters filter5 = new MyFilters();
            MyFilters filter6 = new MyFilters();

            filter1.setImage(thumbImage);
            filter2.setImage(thumbImage);
            filter3.setImage(thumbImage);
            filter4.setImage(thumbImage);
            filter5.setImage(thumbImage);
            filter6.setImage(thumbImage);

            ThumbnailsManager.clearThumbs();
            ThumbnailsManager.addThumb(filter1); //original image

            filter2.setFilter(SampleFilters.getStarLitFilter());
            ThumbnailsManager.addThumb(filter2);

            filter3.setFilter(SampleFilters.getBlueMessFilter());
            ThumbnailsManager.addThumb(filter3);

            filter4.setFilter(SampleFilters.getAweStruckVibeFilter());
            ThumbnailsManager.addThumb(filter4);

            filter5.setFilter(SampleFilters.getLimeStutterFilter());
            ThumbnailsManager.addThumb(filter5);

            filter6.setFilter(SampleFilters.getNightWhisperFilter());
            ThumbnailsManager.addThumb(filter5);


            List<MyFilters> thumbs = ThumbnailsManager.processThumbs(PhotoEditActivity.this);

            FilterRecyclerAdapter adapter = new FilterRecyclerAdapter(thumbs, filter -> {
               Bitmap newImg = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
               mImageView.setImageBitmap(filter.getFilter().processFilter(newImg));
            });
            filter_rc.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
        handler.post(r);
    }
}
