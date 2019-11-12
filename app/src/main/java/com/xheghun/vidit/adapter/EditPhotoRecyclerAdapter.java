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
import com.xheghun.vidit.models.ImageData;

import java.util.List;

public class EditPhotoRecyclerAdapter extends RecyclerView.Adapter<EditPhotoRecyclerAdapter.EditPhotoViewHolder> {

    private List<ImageData> mImagePath;
    private final OnItemClickListener itemClickListenerlistener;
    Context mContext;

    public EditPhotoRecyclerAdapter(Context context, List<ImageData> imagePath, OnItemClickListener itemClickListenerlistener) {
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
        void bind(final ImageData imageData, final OnItemClickListener listener) {
            Glide.with(mContext).load(imageData.getImageBitmap()).override(mImageView.getWidth(),mImageView.getHeight())
                    .into(mImageView);

            itemView.setOnClickListener(v -> listener.onItemClick(imageData));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ImageData imageData);
    }
}
