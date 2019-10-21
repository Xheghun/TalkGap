package com.xheghun.vidit.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xheghun.vidit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryVideoFragment extends Fragment {


    public GalleryVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_video, container, false);
    }

}
