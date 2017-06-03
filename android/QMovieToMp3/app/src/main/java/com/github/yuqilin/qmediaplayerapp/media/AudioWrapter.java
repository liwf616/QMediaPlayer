package com.github.yuqilin.qmediaplayerapp.media;

import com.github.yuqilin.qmediaplayerapp.gui.mp3.AudioListAdapter;

/**
 * Created by liwenfeng on 17/4/20.
 */

public class AudioWrapter {
    public long audioId;
    public String filePath;
    public String mimeType;
    public String title;
    public String duration;
    public String fileSize;
    public String artlist;
    public  int postion;
    public AudioListAdapter.ViewHolder holder;
    public int playStatus;
}
