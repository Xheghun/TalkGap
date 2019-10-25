package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xheghun.vidit.R;

public class EffectsRecyclerAdapter extends RecyclerView.Adapter<EffectsRecyclerAdapter.EffectsViewHolder> {

    private Context mContext;

    public EffectsRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public EffectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.effects_item,parent,false);
        return new EffectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EffectsViewHolder holder, int position) {
        holder.textView.setText("Random Effect");
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class EffectsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public EffectsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.effects_name);
        }
    }
}
