package com.xheghun.vidit.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xheghun.vidit.R;
import com.xheghun.vidit.VideoEditActivity;
import com.xheghun.vidit.adapter.GalleryMediaAdapter;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryVideoFragment extends Fragment {

    private Cursor cursor;
    private List<GalleryMedia> videoList;

    @BindView(R.id.video_recycler_view)
    RecyclerView recyclerView;

    public GalleryVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_video, container, false);
        ButterKnife.bind(this,view);

        getVideos();


        recyclerView.setAdapter(new GalleryMediaAdapter(videoList, getContext(), 2, (media) -> {
            //Toast.makeText(getContext(), "media path is: "+media.getMediaPath(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), VideoEditActivity.class);
            intent.putExtra("video_path",media.getPath());
            startActivity(intent);
        }));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        return view;
    }



    private void getVideos() {

        final String[] columns = {MediaStore.Video.VideoColumns.DATA,MediaStore.Video.Media._ID};
        final String orderBy = MediaStore.Video.Media.DATE_ADDED;
        //Stores all the images from the gallery in Cursor
        cursor = getActivity().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        //Total number of images
        assert cursor != null;
        int count = cursor.getCount();

        videoList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);

            GalleryMedia video = new GalleryMedia();
            video.setPath(cursor.getString(dataColumnIndex));
            videoList.add(video);


        }
        Collections.reverse(videoList);

    }


}
