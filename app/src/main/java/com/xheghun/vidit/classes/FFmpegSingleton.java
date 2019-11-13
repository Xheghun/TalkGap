package com.xheghun.vidit.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class FFmpegSingleton {
    private static final String TAG = "FFMPEG" ;

    Context context;

    private FFmpegSingleton(Context context) {
        this.context = context;
    }

    private static   FFmpeg ffmpeg;
    public void loadFFMpegBinary(Context context) {
        try {
            if (ffmpeg == null) {
                Log.d(TAG, "ffmpeg: null");
                ffmpeg = FFmpeg.getInstance(context);
            }
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    Toast.makeText(context, "failed to load binaries", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "ffmpeg : correct Loaded");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            Toast.makeText(context, "Sorry Your devices doesn't fully support this app", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "EXception not supported : " + e);
        }
    }

}
