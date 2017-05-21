package com.github.yuqilin.qmediaplayerapp.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.github.yuqilin.qmediaplayerapp.R;


public class ShareUtils {

    // 分享文字--全局
    public static void shareAppText(Activity act) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "I'm using a great video editor APP, come and enjoy in Tube Video to MP3 Converter. https://play.google.com/store/apps/details?id=com.github.yuqilin.qmovietomp3");
        shareIntent.setType("text/plain");

        // 设置分享列表的标题，并且每次都显示分享列表
        act.startActivity(Intent.createChooser(shareIntent, "Share To"));
    }

    public static void adviceEmail(Activity act) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto: wenjoyai@gmail.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, act.getString(R.string.app_name));
        act.startActivity(data);
    }
}
