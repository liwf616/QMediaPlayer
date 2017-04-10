package com.github.yuqilin.qmediaplayerapp.media;

/**
 * Created by liwenfeng on 17/4/7.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    protected List<String> command = new ArrayList<String>();

    public void addCommand(String cmd) {
        command.add(cmd);
    }

    public void addCommand(String key, String value) {
        command.add(key);
        command.add(value);
    }

    public void addCommand(List<String> cmd) {
        command.addAll(cmd);
    }

    public void addCommand(String[] cmd) {
        command.addAll(Arrays.asList(cmd));
    }

    public List<String> getCommand() {
        return command;
    }
}