package com.xheghun.vidit.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xheghun.vidit.R;
import com.xheghun.vidit._interface.FilterCallBack;
import com.xheghun.vidit.models.MyFilters;

import java.util.List;

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.FilterRecyclerViewHolder> {

    private static final String TAG = "FILTER_ADAPTER";
    private static int lastPosition = -1;
    private FilterCallBack filterCallBack;
    private List<MyFilters> filters;


    public FilterRecyclerAdapter(List<MyFilters> filters, FilterCallBack callBack) {
        Log.v(TAG,"Filter Adapter has" + filters.size() + "items");
        this.filters = filters;
        this.filterCallBack = callBack;
    }

    @NonNull
    @Override
    public FilterRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG,"On Create View Holder Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item,parent,false);
        return new FilterRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterRecyclerViewHolder holder, int position) {
        final MyFilters filter = filters.get(position);
        Log.v(TAG,"On Bind View Called");
        holder.bind(filter,filterCallBack);
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    class FilterRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView filterNameText;
        FilterRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.filter_image);
            filterNameText = itemView.findViewById(R.id.filter_name);
        }

        void bind(MyFilters filter, FilterCallBack callBack) {
            thumbnail.setImageBitmap(filter.getImage());
            filterNameText.setText(filter.getName());
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                callBack.onFilterClick(filter);
            });
        }
    }
}
