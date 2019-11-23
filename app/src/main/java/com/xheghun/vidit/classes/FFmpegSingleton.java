package com.xheghun.vidit.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class FFmpegSingleton {
    private static final String TAG = "FFMPEG" ;
    private static boolean isCommandSucessful = false;
    private FFmpegSingleton() {}

    private static   FFmpeg ffmpeg;
    public static boolean executeCmD(Context context,String[] cmd) {
            Log.d(TAG, "ffmpeg: null");
            ffmpeg = FFmpeg.getInstance(context);

            try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    Toast.makeText(context, "failed to load binaries", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "ffmpeg : correct Loaded");
                    Toast.makeText(context, "success loading binaries", Toast.LENGTH_SHORT).show();



                    try {
                        ffmpeg.execute(cmd,new ExecuteBinaryResponseHandler(){
                            @Override
                            public void onSuccess(String message) {
                                super.onSuccess(message);
                                Toast.makeText(context, "command success", Toast.LENGTH_SHORT).show();
                                isCommandSucessful = true;
                            }

                            @Override
                            public void onProgress(String message) {
                                super.onProgress(message);
                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                                Toast.makeText(context, "status ->"+message, Toast.LENGTH_LONG).show();
                                Log.d("FFMPEG MESSAGE",message);
                                isCommandSucessful = false;
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        });
                    } catch (FFmpegCommandAlreadyRunningException e) {
                        e.printStackTrace();
                    }




                }
            });
        } catch (FFmpegNotSupportedException e) {
            Toast.makeText(context, "Sorry Your devices doesn't fully support this app", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "EXception not supported : " + e);
        }

        return isCommandSucessful;
    }

}
