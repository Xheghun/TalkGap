package com.xheghun.vidit;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xheghun.vidit.adapter.MusicListAdapter;
import com.xheghun.vidit.models.MusicData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicSelectActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_select);

        RecyclerView recyclerView = findViewById(R.id.music_list);        recyclerView.setHasFixedSize(true);
        player = new MediaPlayer();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicListAdapter(this,getAudioFiles(), data -> {
            if (player.isPlaying()) {
                player.stop();
                player.release();
                playAudio(data.getPath());
            } else {
                playAudio(data.getPath());
            }
            Toast.makeText(MusicSelectActivity.this, data.getPath(), Toast.LENGTH_SHORT).show();
        }));
    }

    private List<MusicData> getAudioFiles() {
        final List<MusicData> musicDataList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.AudioColumns.ARTIST,};
        Cursor cursor = getContentResolver().query(uri,projection,MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null,
                MediaStore.Audio.Media.TITLE );

        if (cursor != null && cursor.getCount() > 0 ) {
            while (cursor.moveToNext()) {
                MusicData musicData = new MusicData();
                String path = cursor.getString(0);
                String name = cursor.getString(1);
                String duration = cursor.getString(2);
                String artist = cursor.getString(3);

                musicData.setMusicName(name);
                musicData.setPath(path);
                musicData.setDuration(duration);
                musicData.setArtist(artist);

                musicDataList.add(musicData);
            }
            cursor.close();
        } else {
            Toast.makeText(this,"No Audio On this device",Toast.LENGTH_SHORT).show();
            finish();
        }
        return musicDataList;
    }
    private void playAudio(String path) {

        Uri uri = Uri.parse(path);
        try {
            player = null;
            player = new MediaPlayer();

            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Sorry File doesn't exist",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.release();
        finish();
    }


}
