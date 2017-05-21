package com.github.yuqilin.qmediaplayerapp.util;

import android.content.Context;
import android.content.Intent;
import com.github.yuqilin.qmediaplayerapp.AboutActivity;

public class NavigationUtils {
//    public static void goToCutter(Context context, BaseModel fileModel) {
//        Intent intent = new Intent(context, CutterActivity.class);
//        Bundle mBundle = new Bundle();
//        mBundle.putSerializable(CutterActivity.INTENT_IN_MODEL, fileModel);
//        intent.putExtras(mBundle);
//        context.startActivity(intent);
//    }

    public static void goToAbout(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

//    public static void goToScan(Context context) {
//        Intent intent = new Intent(context, ScanActivity.class);
//        context.startActivity(intent);
//    }
}
