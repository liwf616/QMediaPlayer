package com.github.yuqilin.qmediaplayerapp.gui.tasks;

import android.util.Log;

import com.github.yuqilin.qmediaplayerapp.QApplication;
import com.github.yuqilin.qmediaplayer.FFmpegInvoke;
import com.googlecode.mp4parser.util.Logger;

import java.util.Arrays;

/**
 * Created by liwenfeng on 17/4/15.
 */

public class TaskRunner implements FFmpegInvoke.FFmpegInvokeListener {
    public static final String TAG = "TaskRunner";

    public interface TaskRunnerListener {
        void onProcess(int seconds);
        void onCompleted();
    }

    private TaskRunner.TaskRunnerListener mTaskRunnerListener;

    public TaskRunner(TaskRunner.TaskRunnerListener taskRunnerListener) {
        mTaskRunnerListener = taskRunnerListener;
    }

    public void  onRunProcess(int seconds) {
        if (mTaskRunnerListener != null) {
            mTaskRunnerListener.onProcess(seconds);
        }
    }

    public void convertStart(final String[] command) {
        QApplication.runBackground(new Runnable() {
            @Override
            public void run() {
                runTranscodeTask(command);

                if (mTaskRunnerListener != null) {
                    mTaskRunnerListener.onCompleted();
                }
            }
        });
    }

    private void runTranscodeTask(String[] command) {
        Log.w(TAG, Arrays.toString(command));

        FFmpegInvoke ffmpeg = new FFmpegInvoke(this);
        ffmpeg.run(command);
    }
}
