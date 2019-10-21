package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xheghun.vidit.R;
import com.xheghun.vidit.models.GalleryMedia;

import java.util.List;

public class EditPhotoRecyclerAdapter extends RecyclerView.Adapter<EditPhotoRecyclerAdapter.EditPhotoViewHolder> {

    private List<GalleryMedia> mImagePath;
    private final OnItemClickListener itemClickListenerlistener;
    Context mContext;

    public EditPhotoRecyclerAdapter(Context context, List<GalleryMedia> imagePath, OnItemClickListener itemClickListenerlistener) {
        mImagePath = imagePath;
        this.itemClickListenerlistener = itemClickListenerlistener;
        mContext = context;
    }

    @NonNull
    @Override
    public EditPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_edit_item,parent,false);
        return new EditPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditPhotoViewHolder holder, int position) {
        holder.bind(mImagePath.get(position),itemClickListenerlistener);

    }

    @Override
    public int getItemCount() {
        return mImagePath.size();
    }

    class EditPhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        public EditPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.photo_image);
        }
        void bind(final GalleryMedia galleryMedia, final OnItemClickListener listener) {
            Glide.with(mContext).load(galleryMedia.getPath()).override(mImageView.getWidth(),mImageView.getHeight())
                    .into(mImageView);

            itemView.setOnClickListener(v -> listener.onItemClick(galleryMedia));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GalleryMedia media);
    }
}
