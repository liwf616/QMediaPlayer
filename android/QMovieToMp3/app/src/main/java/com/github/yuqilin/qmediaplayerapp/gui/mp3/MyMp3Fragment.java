package com.github.yuqilin.qmediaplayerapp.gui.mp3;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.github.yuqilin.qmediaplayerapp.BaseFragment;
import com.github.yuqilin.qmediaplayerapp.IAudioEventHandler;
import com.github.yuqilin.qmediaplayerapp.IEventsHandler;
import com.github.yuqilin.qmediaplayerapp.MainActivity;
import com.github.yuqilin.qmediaplayerapp.R;
import com.github.yuqilin.qmediaplayerapp.TrimmerActivity;
import com.github.yuqilin.qmediaplayerapp.gui.video.VideoListAdapter;
import com.github.yuqilin.qmediaplayerapp.gui.view.AutoFitRecyclerView;
import com.github.yuqilin.qmediaplayerapp.media.AudioLoader;
import com.github.yuqilin.qmediaplayerapp.media.AudioWrapter;
import com.github.yuqilin.qmediaplayerapp.media.VideoWrapper;
import com.github.yuqilin.qmediaplayerapp.util.FileUtils;

import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by liwenfeng on 17/3/26.
 */

public class MyMp3Fragment extends BaseFragment implements IAudioEventHandler, AudioLoader.AudioLoaderListener{
    private static final String PAGE_TITLE = "My Audios";
    public static final String TAG = "AudioFragment";

    protected AutoFitRecyclerView mGridView;
    private AudioListAdapter mAudioAdapter;
    private AudioLoader mAudioLoader;
    Context context;

    private TextView mPathTv;
    private LinearLayout mOpenFilell;

    public static final int SCAN_START = 1;
    public static final int SCAN_FINISH = 2;
    public static final int SCAN_CANCEL = 3;
    public static final int SCAN_ADD_ITEM = 4;

    public MyMp3Fragment() {
        super();
    }

    public MyMp3Fragment(Context ctx) {
        context = ctx;
    }


    MediaPlayer mediaPlayer;
    AudioWrapter currentItem;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_START:
                    mAudioLoader.scanStart();
                    break;
                case SCAN_FINISH:
                    mAudioAdapter.updateAudios(mAudioLoader.getAudios());
                    break;
                case SCAN_CANCEL:
                    break;
                case SCAN_ADD_ITEM:
                    mAudioAdapter.addAudio((AudioWrapter)msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mp3;
    }

    @Override
    protected String getPageTitle() {
        return PAGE_TITLE;
    }

    protected void initView(View view, Bundle savedInstanceState) {
        mAudioLoader = new AudioLoader(this);

        mGridView = (AutoFitRecyclerView) view.findViewById(R.id.audio_grid);

        mAudioAdapter = new AudioListAdapter((MainActivity) context, this);

        mGridView.setAdapter(mAudioAdapter);

        mHandler.sendEmptyMessage(SCAN_START);

        mediaPlayer = new MediaPlayer();

        mPathTv = (TextView) view.findViewById(R.id.path_tv);
        mPathTv.setText(FileUtils.getStoragePath());

        mOpenFilell = (LinearLayout)view.findViewById(R.id.openll);
        mOpenFilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignFolder(FileUtils.getStoragePath());
            }
        });
    }


    //使用文件管理器打开指定文件夹
    private void openAssignFolder(String path){
        File file = new File(path);
        if(null==file || !file.exists()){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "audio/*");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioAdapter.clear();
    }

    public void stopPlaying() {
        if (mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void play (AudioWrapter item) {
        if (mediaPlayer.isPlaying()){
            Log.d("progress ", "isPlaying");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.reset();
        }

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(item.filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "sorry, play audio failed, my friend!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(context, "sorry, play audio failed, my friend!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

        mediaPlayer.start();
    }

    public void pause(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    /*处理歌曲的播放及暂停的逻辑
    * 1.如果当前歌曲正在播放，则暂停该音乐，并设置图标
    * 2.如果当前歌曲并不在播放，则更新前一首歌曲的图标，然后开始播放当前歌曲
    * */

    public void onClick(View v, int position, AudioWrapter item) {
        if (item.playStatus == 0) {
            play(item);
            item.playStatus = 1;
        } else {
            pause();
            item.playStatus = 0;
        }

        if (currentItem != null && item != currentItem){
            currentItem.playStatus = 0;
        }

        currentItem = item;
        mAudioAdapter.notifyDataSetChanged();
    }

    public void onDelete(AudioWrapter item) {
        if ( currentItem != null &&
                currentItem == item &&
                currentItem.playStatus == 1) {
            stop();
        }
    }

    @Override
    public boolean onLongClick(View v, int position, AudioWrapter item) {
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
            mAudioAdapter.setGridCardWidth(mGridView.getColumnWidth());
        }

        mGridView.setNumColumns(listMode ? 1 : -1);
        if (mAudioAdapter.isListMode() != listMode) {
            mAudioAdapter.setListMode(listMode);
        }
    }

    @Override
    public void onLoadItem(int position, AudioWrapter audio) {

    }

    @Override
    public void onLoadCompleted(ArrayList<AudioWrapter> audios) {
        mHandler.sendEmptyMessage(SCAN_FINISH);
    }

    public void addAudio(AudioWrapter  audio) {
        Message message = Message.obtain();
        message.obj = audio;
        message.what = SCAN_ADD_ITEM;

        mHandler.sendMessage(message);
    }
}
