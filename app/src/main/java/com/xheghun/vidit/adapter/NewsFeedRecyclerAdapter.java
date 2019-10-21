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

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.NewsFeedViewHolder> {

    private Context mContext;

    public NewsFeedRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        Glide.with(mContext).load(R.drawable.splash_bg)
                .override(holder.profile_img.getWidth(),holder.profile_img.getHeight())
                .into(holder.profile_img);
        Glide.with(mContext).load(R.drawable.img_1)
                .override(holder.post_img.getWidth(),holder.post_img.getHeight())
                .into(holder.post_img);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class NewsFeedViewHolder extends RecyclerView.ViewHolder{
        ImageView profile_img, post_img;
        public NewsFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.profile_img);
            post_img = itemView.findViewById(R.id.post_img);
        }
    }
}
