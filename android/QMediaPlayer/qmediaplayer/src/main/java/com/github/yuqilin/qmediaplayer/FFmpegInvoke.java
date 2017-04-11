package com.github.yuqilin.qmediaplayer;

import android.util.Log;

/**
 * Created by yuqilin on 17/1/13.
 */

public final class FFmpegInvoke {
    private static final String TAG = "VideoTranscode";
    static {
        System.loadLibrary("ffmpeg_invoke");
    }

    private String mVideoPath;
    private String mVideoDestPath;
    private String mBits;
    private String mType;
    private String mVbr;

    public static void help() {
        FFmpegInvoke ffmpeg = new FFmpegInvoke();
        ffmpeg.run(new String[]{
                "ffmpeg", "-h"
        });
    }

    public void updateProcess(int seconds) {
        Log.w(TAG, "process= " + seconds);
    }

    public native void run(String[] args);
}
