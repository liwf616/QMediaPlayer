package com.github.yuqilin.qmediaplayerapp.gui.tasks;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.github.yuqilin.qmediaplayerapp.BaseFragment;
import com.github.yuqilin.qmediaplayerapp.R;
import com.github.yuqilin.qmediaplayerapp.gui.view.AutoFitRecyclerView;
import com.github.yuqilin.qmediaplayerapp.media.MediaTask;
import com.github.yuqilin.qmediaplayerapp.util.ITaskEventHandler;

/**
 * Created by liwenfeng on 17/3/26.
 */

public class TaskFragment extends BaseFragment implements ITaskEventHandler {
    public static final String TAG = "TaskFragment";
    private static final String PAGE_TITLE = "TASKS";

    protected AutoFitRecyclerView mGridView;
    private TaskListAdapter mTaskListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }

    protected void initView(View view, Bundle savedInstanceState) {
        mGridView = (AutoFitRecyclerView) view.findViewById(R.id.task_grid);
        mTaskListAdapter = new TaskListAdapter(this);
        mGridView.setAdapter(mTaskListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        updateViewMode();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(KEY_GROUP, mGroup);
    }

    @Override
    public void onDestroyView() {
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiverVideoListFragment);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mVideoAdapter.clear();
    }

    @Override
    public void onClick(View v, int position, MediaTask item) {

    }

    @Override
    public boolean onLongClick(View v, int position, MediaTask item) {
        return false;
    }


    private void updateViewMode() {
        if (getView() == null || getActivity() == null) {
            Log.w(TAG, "Unable to setup the view");
            return;
        }

        Resources res = getResources();

        boolean listMode = res.getBoolean(R.bool.list_mode);
        listMode |= res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("force_list_portrait", false);

        // Compute the left/right padding dynamically
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        // Select between grid or list
        if (!listMode) {
            int thumbnailWidth = res.getDimensionPixelSize(R.dimen.grid_card_thumb_width);
            mGridView.setColumnWidth(mGridView.getPerfectColumnWidth(thumbnailWidth, res.getDimensionPixelSize(R.dimen.default_margin)));
//            mTaskListAdapter.setGridCardWidth(mGridView.getColumnWidth());
        }

        mGridView.setNumColumns(listMode ? 1 : -1);

//        if (mVideoAdapter.isListMode() != listMode) {
//            mVideoAdapter.setListMode(listMode);
//        }
    }
}
