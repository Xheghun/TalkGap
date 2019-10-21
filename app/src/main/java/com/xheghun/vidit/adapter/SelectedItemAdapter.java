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


public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.SelectedImageViewHolder> {

    private Context mContext;
    private List<String> pathList;

    public SelectedItemAdapter(Context context, List<String> pathList) {
        mContext = context;
        this.pathList = pathList;
    }

    @NonNull
    @Override
    public SelectedImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_frame,parent,false);
        return new SelectedImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedImageViewHolder holder, int position) {
        holder.bind(pathList.get(position));
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }


    class SelectedImageViewHolder extends RecyclerView.ViewHolder {
        ImageView selectedImage;
        public SelectedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedImage = itemView.findViewById(R.id.image_item);
        }
        void bind(String path) {
            Glide.with(mContext).load(path).override(selectedImage.getWidth(),selectedImage.getHeight())
                    .into(selectedImage);
        }
    }

}