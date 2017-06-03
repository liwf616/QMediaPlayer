package com.github.yuqilin.qmediaplayerapp;

import android.view.View;

import com.github.yuqilin.qmediaplayerapp.media.AudioWrapter;

/**
 * Created by liwenfeng on 17/4/20.
 */

public interface IAudioEventHandler {
    void onClick(View v, int position, AudioWrapter item);
    boolean onLongClick(View v, int position, AudioWrapter item);
    void onDelete(AudioWrapter item);
}
