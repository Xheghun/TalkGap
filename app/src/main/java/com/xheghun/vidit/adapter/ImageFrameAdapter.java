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

import java.util.ArrayList;

public class ImageFrameAdapter extends RecyclerView.Adapter<ImageFrameAdapter.ImageFrameViewHolder> {

    private ArrayList<String> list;
    private Context mContext;

    public ImageFrameAdapter(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageFrameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageFrameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
