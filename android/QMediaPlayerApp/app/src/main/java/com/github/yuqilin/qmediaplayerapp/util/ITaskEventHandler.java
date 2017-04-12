package com.github.yuqilin.qmediaplayerapp.util;

import android.view.View;

import com.github.yuqilin.qmediaplayerapp.media.MediaInfo;

/**
 * Created by liwenfeng on 17/4/12.
 */

public interface ITaskEventHandler {
        void onClick(View v, int position, MediaInfo item);
        boolean onLongClick(View v, int position, MediaInfo item);
}
