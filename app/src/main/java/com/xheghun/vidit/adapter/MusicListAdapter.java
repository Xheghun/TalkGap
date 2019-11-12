package com.xheghun.vidit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xheghun.vidit.R;
import com.xheghun.vidit._interface.MusicItemClickListener;
import com.xheghun.vidit.models.MusicData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {
    private Context context;
    private List<MusicData> musicDataList;
    private MusicItemClickListener itemClickListener;

    public MusicListAdapter(Context context, List<MusicData> musicDataList, MusicItemClickListener itemClickListener) {
        this.context = context;
        this.musicDataList = musicDataList;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item,parent,false);
        return new MusicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        MusicData data = musicDataList.get(position);
        holder.bind(data,itemClickListener);
    }

    @Override
    public int getItemCount() {
        return musicDataList.size();
    }

    class MusicListViewHolder extends RecyclerView.ViewHolder {
        TextView music_name, music_duration, artist_name;
        CircleImageView cover;
        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);
            music_name = itemView.findViewById(R.id.song_name);
            music_duration = itemView.findViewById(R.id.song_duration);
            artist_name = itemView.findViewById(R.id.artist_name);
            cover = itemView.findViewById(R.id.album_cover);
        }


        void bind(MusicData data, final MusicItemClickListener listener) {
            cover.setImageResource(R.drawable.logo);
            music_name.setText(data.getMusicName());
            music_duration.setText("00.00");
            artist_name.setText(data.getArtist());
            itemView.setOnClickListener( v -> listener.onItemClick(data));
        }
    }
}