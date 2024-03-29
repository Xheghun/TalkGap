package com.xheghun.vidit.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
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

public class GalleryMediaAdapter extends RecyclerView.Adapter<GalleryMediaAdapter.GalleryImageViewHolder> {

    private List<GalleryMedia> images;
    private Context mContext;
    private final GalleryItemClickListener itemClickListener;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    //type 1 for image 2 for video
    private int type;

    public GalleryMediaAdapter(List<GalleryMedia> images, Context mContext, int type, GalleryItemClickListener itemClickListener) {
        this.images = images;
        this.mContext = mContext;
        this.type = type;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type == 1)
        view = LayoutInflater.from(mContext).inflate(R.layout.gallery_image_item,parent,false);
        else if(type == 2)
        view = LayoutInflater.from(mContext).inflate(R.layout.video_gallery_item,parent,false);
        else
            view = null;
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageViewHolder holder, int position) {
        holder.bind(images.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView mediaSelected;
        GalleryImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaSelected = itemView.findViewById(R.id.item_selected);
            imageView = itemView.findViewById(R.id.media_item);
        }

        void bind(final GalleryMedia media, final GalleryItemClickListener listener) {
           // Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
            Glide.with(mContext).load(media.getPath())
                    .override(imageView.getWidth(),imageView.getHeight())
                    .into(imageView);
            itemView.setOnClickListener(v -> {
                int adapaterPosition = getAdapterPosition();
                if (!itemStateArray.get(adapaterPosition, false) && mediaSelected != null ) {
                    if (itemStateArray.size() <= 16) {
                            mediaSelected.setImageResource(R.drawable.ic_verified);
                            itemStateArray.put(adapaterPosition, true);
                    }
                } else {
                    if (mediaSelected != null) {
                        mediaSelected.setImageResource(0);
                    }
                    itemStateArray.put(adapaterPosition,false);
                }
                listener.onItemClick(media, itemStateArray);
            });
        }
    }

     public interface GalleryItemClickListener {
        void onItemClick(GalleryMedia media, SparseBooleanArray itemsArray);
    }
}
