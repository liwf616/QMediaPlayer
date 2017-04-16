package com.github.yuqilin.qmediaplayerapp.util;

import android.view.View;

import com.github.yuqilin.qmediaplayerapp.media.MediaTask;

/**
 * Created by liwenfeng on 17/4/12.
 */

public interface ITaskEventHandler {
        void onClick(View v, int position, MediaTask item);
        boolean onLongClick(View v, int position, MediaTask item);
}