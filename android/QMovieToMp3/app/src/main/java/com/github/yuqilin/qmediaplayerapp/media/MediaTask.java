package com.github.yuqilin.qmediaplayerapp.media;


import com.github.yuqilin.qmediaplayerapp.gui.tasks.Command;
import com.github.yuqilin.qmediaplayerapp.util.FileUtils;
import com.github.yuqilin.qmediaplayerapp.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by liwenfeng on 17/4/7.
 */

public class MediaTask {

    private String  videoPath; //绝对路径

    private int vbr;
    private String  type;
    private String  bits;
    private int     duration;

    private int     startTime;

    private int     endTime;

    private int    process;

    private String  videoDstPath;

    public int getVbr() {
        return vbr;
    }

    public void setVbr(int vbr) {
        this.vbr = vbr;
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

    public String getVideoDstPath() {
        return videoDstPath;
    }

    public void setVideoDstPath(String videoDstPath) {
        this.videoDstPath = videoDstPath;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public MediaTask(String videoPath, int vbr, String type, String bits, int duration,int startTime, int endTime) {
        this.videoPath = videoPath;
        this.vbr = vbr;
        this.type = type;
        this.bits = bits;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;

        Date now = new Date();
        SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyyMMddHHmmss");

        String time = simpleDate.format(now);

        String destPath = FileUtils.getStoragePath();
        if (destPath == null) {
            return;
        }

        String filename = getFileName(videoPath);
        String destName = null;
        if(filename != null) {
            Random random = new Random(100);

            destName  = filename + time + "." + type;
        } else {
            return;
        }

        this.videoDstPath =  destPath + destName;
    }

    public String getFileName(String pathandname){
        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if (start!=-1 && end!=-1) {
            return pathandname.substring(start+1, end);
        }
        else {
            return null;
        }
    }

    public static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
    }


    public String[] getCommand() {
        Command command = new Command();
        command.addCommand("ffmpeg");
        command.addCommand("-y");
        command.addCommand("-ss", generateTime(startTime));
        command.addCommand("-t", generateTime(endTime - startTime));
        command.addCommand("-i", videoPath);
        command.addCommand("-vn");

        if(bits.equals("copy")) {
            command.addCommand("-c:a", bits);
        } else {
            command.addCommand("-c:a",type);
            if (vbr == 0){
                command.addCommand("-b:a",bits);
            } else {
                command.addCommand("-vbr", String.valueOf(vbr));
            }
        }

        command.addCommand(this.videoDstPath);

        List<String> comList =  command.getCommand();

        String[] commandStr = new String[comList.size()];

        commandStr =  comList.toArray(commandStr);

        return  commandStr;
    }

    public String getProcessText() {
        String process = Util.generateTime(getProcess());
        String duration = Util.generateTime(getEndTime() - getStartTime());

        return String.format("%s/%s", process, duration);
    }

    public int getProcessInt() {
        if(endTime - startTime <=0 ){
            return 100;
        }else {
            return (int) (process * 100 / (endTime - startTime));
        }
    }
}
