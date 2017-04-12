package com.github.yuqilin.qmediaplayerapp.media;

/**
 * Created by liwenfeng on 17/4/7.
 */

public class MediaInfo {

    public String filePath; //绝对路径
    public String mimeType;

    public static final String[] MEDIA_AUDIO_FORMAT = {
            "mp3",
            "aac"
    };

    public static final String[] MEDIA_AUDIO_BITS= {
            "copy",
            "128k",
            "192k",
            "256k",
            "320k",
            "130k",
            "190k",
            "245k"
    };

    public static String getComment(int i) {
        if(i == 0) {
            return "copy (32kb/s)";
        } else  if (i <= 4) {
            return MEDIA_AUDIO_BITS[i] + " " + "CBR";
        } else {
            return MEDIA_AUDIO_BITS[i] + " " + "VBR(slow)";
        }
    }
}
