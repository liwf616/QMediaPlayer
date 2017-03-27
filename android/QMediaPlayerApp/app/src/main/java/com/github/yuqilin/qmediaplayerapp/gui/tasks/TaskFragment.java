package com.github.yuqilin.qmediaplayerapp.gui.tasks;

import android.os.Bundle;
import android.view.View;

import com.github.yuqilin.qmediaplayerapp.BaseFragment;
import com.github.yuqilin.qmediaplayerapp.R;

/**
 * Created by liwenfeng on 17/3/26.
 */

public class TaskFragment extends BaseFragment {
    private static final String PAGE_TITLE = "TASKS";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }

    protected void initView(View view, Bundle savedInstanceState) {}
}
