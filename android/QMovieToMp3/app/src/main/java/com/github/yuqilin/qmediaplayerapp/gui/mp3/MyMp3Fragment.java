package com.github.yuqilin.qmediaplayerapp.gui.mp3;

import android.os.Bundle;
import android.view.View;

import com.github.yuqilin.qmediaplayerapp.BaseFragment;
import com.github.yuqilin.qmediaplayerapp.R;

/**
 * Created by liwenfeng on 17/3/26.
 */

public class MyMp3Fragment extends BaseFragment {
    private static final String PAGE_TITLE = "MY MP3";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mp3;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }

    protected void initView(View view, Bundle savedInstanceState) {}
}
