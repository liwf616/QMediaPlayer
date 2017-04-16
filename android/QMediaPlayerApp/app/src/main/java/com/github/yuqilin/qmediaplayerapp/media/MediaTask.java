package com.github.yuqilin.qmediaplayerapp.media;

import com.github.yuqilin.qmediaplayerapp.gui.tasks.Command;

import java.util.List;

/**
 * Created by liwenfeng on 17/4/7.
 */

public class MediaTask {

    private String videoPath; //绝对路径
    private boolean vbr;
    private String  type;
    private String  bits;
    private long     duration;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVbr() {
        return vbr;
    }

    public void setVbr(boolean vbr) {
        this.vbr = vbr;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public MediaTask(String videoPath, boolean vbr, String type, String bits, long duration) {
        this.videoPath = videoPath;
        this.vbr = vbr;
        this.type = type;
        this.bits = bits;
        this.duration = duration;
    }

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

    public String[] getCommand() {
        Command command = new Command();
        command.addCommand("ffmpeg");
        command.addCommand("-y");
        command.addCommand("-i", videoPath);
        command.addCommand("-c:a","libfdk_aac");
        command.addCommand("-vn");
        command.addCommand("/sdcard/Download/ss_audio.aac");

        List<String> com =  command.getCommand();
        return (String[]) com.toArray(new String[0]);
    }
}
