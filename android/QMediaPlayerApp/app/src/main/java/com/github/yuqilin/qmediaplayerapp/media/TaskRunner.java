package com.github.yuqilin.qmediaplayerapp.media;

import com.github.yuqilin.qmediaplayerapp.QApplication;
import com.github.yuqilin.qmediaplayer.FFmpegInvoke;

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

    public void convertStart() {
        QApplication.runBackground(new Runnable() {
            @Override
            public void run() {
                runTranscodeTask();

                if (mTaskRunnerListener != null) {
                    mTaskRunnerListener.onCompleted();
                }
            }
        });
    }

    private void runTranscodeTask() {
        FFmpegInvoke ffmpeg = new FFmpegInvoke(this);
        ffmpeg.help();
    }
}
