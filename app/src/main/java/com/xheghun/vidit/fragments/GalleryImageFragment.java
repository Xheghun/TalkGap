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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.xheghun.vidit.PhotoEditActivity;
import com.xheghun.vidit.R;
import com.xheghun.vidit.adapter.GalleryMediaAdapter;
import com.xheghun.vidit.adapter.SelectedItemAdapter;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryImageFragment extends Fragment {

    static final int REQUEST_PERMISSION_KEY = 1;

    @BindView(R.id.gallery_images_rc)
    RecyclerView recyclerView;

    private Cursor cursor;
    private List<GalleryMedia> imagesList;
    private List<String> selectedImageList;

    public GalleryImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_image, container, false);



        ButterKnife.bind(this, view);
        getImages();


        RecyclerView selectedImages_rc = view.findViewById(R.id.selected_photos_rc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        selectedImages_rc.setLayoutManager(layoutManager);


        selectedImageList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        recyclerView.setAdapter(new GalleryMediaAdapter(imagesList, getContext(), 1, media -> {

            //check if image is already selected
            if (!selectedImageList.contains(media.getPath())) {
                selectedImageList.add(media.getPath());

            }else if (selectedImageList.contains(media.getPath())) {
                selectedImageList.remove(media.getPath());
            }

            //populate recyclerview
            selectedImages_rc.setAdapter(new SelectedItemAdapter(getContext(),selectedImageList));

            if (selectedImageList.size() >= 1)
                view.findViewById(R.id.selected_photos_layout).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.selected_photos_layout).setVisibility(View.GONE);




            MaterialButton button = view.findViewById(R.id.selected_next_btn);
            if (selectedImageList.size() > 3 && selectedImageList.size() < 17) {
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(v -> {
                   Intent intent = new Intent(getContext(), PhotoEditActivity.class);
                    String[] images = new String[selectedImageList.size()];

                    for (int i = 0; i < selectedImageList.size();i++) {
                        images[i] = selectedImageList.get(i);
                    }

                    intent.putExtra("images",images);
                   startActivity(intent);
                });
            } else {
                button.setVisibility(View.GONE);
            }
        }));
        return view;
    }


    private void getImages() {
        final String[] columns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        //Stores all the images from the gallery in Cursor
        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        //Total number of images
        assert cursor != null;
        int count = cursor.getCount();

        imagesList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            GalleryMedia image = new GalleryMedia();
            image.setPath(cursor.getString(dataColumnIndex));
            imagesList.add(image);
        }

    }
}
