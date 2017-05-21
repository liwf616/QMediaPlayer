package com.github.yuqilin.qmediaplayerapp.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.github.yuqilin.qmediaplayerapp.R;


public class ShareUtils {
    public static void adviceEmail(Activity act) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto: wenjoyai@gmail.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, act.getString(R.string.app_name));
        act.startActivity(data);
    }
}
