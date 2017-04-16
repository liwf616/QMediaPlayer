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

    public interface FFmpegInvokeListener {
        void onRunProcess(int seconds);
    }

    private FFmpegInvokeListener mFFmpegInvokeListener;

    public FFmpegInvoke() {}

    public  FFmpegInvoke(FFmpegInvokeListener ffmpegInvokeListener) {
        mFFmpegInvokeListener = ffmpegInvokeListener;
    }

    public static void help() {
        FFmpegInvoke ffmpeg = new FFmpegInvoke();
        ffmpeg.run(new String[]{
                "ffmpeg", "-h"
        });
    }

    public void updateProcess(int seconds) {
        Log.w(TAG, "process= " + seconds);

        if (mFFmpegInvokeListener != null) {
            mFFmpegInvokeListener.onRunProcess(seconds);
        }
    }

    public native void run(String[] args);
}
