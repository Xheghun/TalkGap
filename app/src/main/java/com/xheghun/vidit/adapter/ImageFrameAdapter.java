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

import java.util.List;

public class ImageFrameAdapter extends RecyclerView.Adapter<ImageFrameAdapter.ImageFrameViewHolder> {

    private List<String> list;
    private Context mContext;
    private ImageFrameCancelClick cancelClick;


    public ImageFrameAdapter(List<String> list, Context mContext, ImageFrameCancelClick cancelClick) {
        this.list = list;
        this.mContext = mContext;
        this.cancelClick = cancelClick;
    }

    @NonNull
    @Override
    public ImageFrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_frame,parent,false);
        return new ImageFrameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFrameViewHolder holder, int position) {
        String  image = list.get(position);
        Glide.with(mContext).load(image)
                .override(holder.imageView.getWidth(),holder.imageView.getHeight())
                .into(holder.imageView);
        holder.cancel_btn.setOnClickListener(v -> cancelClick.onClick(image,position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageFrameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView cancel_btn;
        ImageFrameViewHolder(@NonNull View itemView) {
            super(itemView);
            cancel_btn = itemView.findViewById(R.id.image_cancel);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }

    public interface ImageFrameCancelClick {
        void onClick(String path,int position);
    }
}
