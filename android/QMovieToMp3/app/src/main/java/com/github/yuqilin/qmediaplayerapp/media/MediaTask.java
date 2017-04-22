package com.github.yuqilin.qmediaplayerapp.media;

import com.github.yuqilin.qmediaplayerapp.VideoPlayerActivity;
import com.github.yuqilin.qmediaplayerapp.gui.tasks.Command;
import com.github.yuqilin.qmediaplayerapp.util.Strings;

import java.util.List;

/**
 * Created by liwenfeng on 17/4/7.
 */

public class MediaTask {

    private String videoPath; //绝对路径
    private boolean vbr;
    private String  type;
    private String  bits;
    private int    duration;

    public static String[] getMediaAudioFormat() {
        return MEDIA_AUDIO_FORMAT;
    }

    private int    process;

    private int     taskIndex;

    public int getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getProcess() {
        return  process;
    }

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

    public void setDuration(int duration) {
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

    public MediaTask(String videoPath, boolean vbr, String type, String bits, int duration) {
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
        command.addCommand("-ab");
        command.addCommand("32000");
        command.addCommand("-ar");
        command.addCommand("44100");
        command.addCommand("-ac");
        command.addCommand("2");
        command.addCommand("/sdcard/Download/ss_audio.mp4");

        List<String> comList =  command.getCommand();

        String[] commandStr = new String[comList.size()];

        commandStr =  comList.toArray(commandStr);

        return  commandStr;
    }

    public String getProcessText() {
        String process = VideoPlayerActivity.generateTime(getProcess());
        String duration = VideoPlayerActivity.generateTime(getDuration());

        return String.format("%s/%s", process, duration);
    }

    public int getProcessInt() {
        return (int) (process * 100 / duration);
    }
}
