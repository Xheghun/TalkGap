package com.xheghun.vidit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xheghun.vidit.fragments.MediaMenuFragment;

public class MediaMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_menu);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.media_container,new MediaMenuFragment()).commit();
    }

}
