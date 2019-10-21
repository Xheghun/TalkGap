package com.xheghun.vidit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.xheghun.vidit.FourSnapActivity;
import com.xheghun.vidit.GalleryActivity;
import com.xheghun.vidit.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaMenuFragment extends Fragment {

    public MediaMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_media_menu, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.media_cam_btn)
    public void gotoCam(){
        startActivity(new Intent(getContext(), FourSnapActivity.class));

    }

    @OnClick(R.id.media_video_btn)
    public void  gotoVideo() {
        Toast.makeText(getContext(),"Meet Dev 2",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.media_gallery_btn)
    public void gotoGallery() {
        startActivity(new Intent(getContext(), GalleryActivity.class));
    }


}
