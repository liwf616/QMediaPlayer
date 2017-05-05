/*****************************************************************************
 * UiTools.java
 *****************************************************************************
 * Copyright © 2011-2014 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package com.github.yuqilin.qmediaplayerapp.util;

import android.annotation.TargetApi;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Util {
    public final static String TAG = "VLC/Util";

    public static String readAsset(String assetName, String defaultS) {
        InputStream is = null;
        BufferedReader r = null;
        try {
//            is = VLCApplication.getAppResources().getAssets().open(assetName);
            r = new BufferedReader(new InputStreamReader(is, "UTF8"));
            StringBuilder sb = new StringBuilder();
            String line = r.readLine();
            if(line != null) {
                sb.append(line);
                line = r.readLine();
                while(line != null) {
                    sb.append('\n');
                    sb.append(line);
                    line = r.readLine();
                }
            }
            return sb.toString();
        } catch (IOException e) {
            return defaultS;
        } finally {
            close(is);
            close(r);
        }
    }

    @TargetApi(android.os.Build.VERSION_CODES.GINGERBREAD)
    public static void commitPreferences(SharedPreferences.Editor editor){
        if (AndroidUtil.isGingerbreadOrLater())
            editor.apply();
        else
            editor.commit();
    }

    public static boolean close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
                return true;
            } catch (IOException e) {}
        return false;
    }

//    public static boolean isCallable(Intent intent) {
//        List<ResolveInfo> list = VLCApplication.getAppContext().getPackageManager().queryIntentActivities(intent,
//                PackageManager.MATCH_DEFAULT_ONLY);
//        return list.size() > 0;
//    }

    //将字节数转化为MB
    public static  String byteToMB(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return String.format("%.1f GB",(float)size/gb);
        }else if (size >= mb){
            float f = (float) size/mb;
            return String.format(f > 100 ?"%.0f MB":"%.1f MB",f);
        }else if (size > kb){
            float f = (float) size / kb;
            return String.format(f>100?"%.0f KB":"%.1f KB",f);
        }else {
            return String.format("%d B",size);
        }
    }

    public static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }
}
