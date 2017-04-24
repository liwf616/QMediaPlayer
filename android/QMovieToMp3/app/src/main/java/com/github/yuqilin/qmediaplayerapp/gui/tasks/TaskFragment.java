package com.github.yuqilin.qmediaplayerapp.gui.tasks;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.github.yuqilin.qmediaplayerapp.BaseFragment;
import com.github.yuqilin.qmediaplayerapp.MainActivity;
import com.github.yuqilin.qmediaplayerapp.QApplication;
import com.github.yuqilin.qmediaplayerapp.R;
import com.github.yuqilin.qmediaplayerapp.gui.mp3.MyMp3Fragment;
import com.github.yuqilin.qmediaplayerapp.gui.view.AutoFitRecyclerView;
import com.github.yuqilin.qmediaplayerapp.media.AudioWrapter;
import com.github.yuqilin.qmediaplayerapp.media.MediaTask;
import com.github.yuqilin.qmediaplayerapp.ITaskEventHandler;

/**
 * Created by liwenfeng on 17/3/26.
 */

public class TaskFragment extends BaseFragment implements ITaskEventHandler {
    public static final String TAG = "TaskFragment";
    private static final String PAGE_TITLE = "TASKS";

    protected AutoFitRecyclerView mGridView;
    private TaskListAdapter mTaskListAdapter;
    private MainActivity mainActivity;

    public static final int SCAN_START = 1;
    public static final int SCAN_FINISH = 2;
    public static final int SCAN_CANCEL = 3;
    public static final int SCAN_ADD_ITEM = 4;

    public TaskFragment(MainActivity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_START:
                    break;
                case SCAN_FINISH:
//                    mVideoAdapter.updateVideos(mVideoLoader.getVideos());
                    break;
                case SCAN_CANCEL:
                    break;
                case SCAN_ADD_ITEM:
                    mTaskListAdapter.addVideo((MediaTask) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


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
        mTaskListAdapter = new TaskListAdapter(this, mGridView);
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

    @Override
    public boolean onTaskFinished(MediaTask task) {
        MediaScannerConnection.scanFile(mActivity, new String[] { task.getVideoDstPath() }, null, null);
        if (mainActivity!=null) {
            MyMp3Fragment myMp3Fragment = mainActivity.getmMyMp3Fragment();
            if (myMp3Fragment != null) {
                AudioWrapter media = new AudioWrapter();
                media.artlist = "<unknown>";
                media.duration = String.valueOf(task.getEndTime() - task.getStartTime());
                media.filePath = task.getVideoDstPath();
                myMp3Fragment.addAudio(media);
            }
        }
        return  true;
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
    }

    public void addTask(MediaTask task) {
        Message message = Message.obtain();
        message.obj = task;
        message.what = SCAN_ADD_ITEM;
        mHandler.sendMessage(message);
    }
}
