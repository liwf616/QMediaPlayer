package com.github.yuqilin.qmediaplayerapp.gui.mp3;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.yuqilin.qmediaplayerapp.IAudioEventHandler;
import com.github.yuqilin.qmediaplayerapp.R;
import com.github.yuqilin.qmediaplayerapp.VideoPlayerActivity;
import com.github.yuqilin.qmediaplayerapp.media.AudioWrapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwenfeng on 17/4/20.
 */

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {
    public final static String TAG = "AudioListAdapter";
    private IAudioEventHandler mEventsHandler;

    private ArrayList<AudioWrapter> mAudios = new ArrayList<>();
    private boolean mListMode = true;
    private int mGridCardWidth = 0;


    public AudioListAdapter(IAudioEventHandler eventsHandler) {
        super();
        mEventsHandler = eventsHandler;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder viewType " + viewType);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(mListMode ? R.layout.item_audio_list : R.layout.item_video_grid, parent, false);


        if (!mListMode) {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) v.getLayoutParams();
            params.width = mGridCardWidth;
            params.height = params.width * 10 / 16;
            v.setLayoutParams(params);
        }

        return new ViewHolder(v);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AudioWrapter media = (AudioWrapter) view.getTag();
            mEventsHandler.onClick(view, media.postion, media);
        }
    };

    public void onBindViewHolder(AudioListAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder position " + position);

        AudioWrapter media = mAudios.get(position);

        if (media == null) {
            return;
        }

        media.postion = position;
        media.holder = holder;

        Log.d(TAG, "position[" + position + "]: " + media.filePath);
        holder.mFileName.setText(media.filePath.substring(media.filePath.lastIndexOf('/') + 1));
        try {
            holder.mDuration.setText(VideoPlayerActivity.generateTime(Integer.parseInt(media.duration)));
        } catch (NumberFormatException e) {

        }

        holder.mListItem.setTag(media);
        holder.mListItem.setOnClickListener(mOnClickListener);
    }

    public void onBindViewHolder(AudioListAdapter.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Log.d(TAG, "onBindViewHolder position " + position + ", payloads size " + payloads.size());
            onBindViewHolder(holder, position);
        }
    }

    public void onViewRecycled(AudioListAdapter.ViewHolder holder) {
        Log.d(TAG, "onViewRecycled");
        super.onViewRecycled(holder);
    }

    public void setListMode(boolean value) {
        mListMode = value;
    }

    public boolean isListMode() {
        return mListMode;
    }

    void setGridCardWidth(int gridCardWidth) {
        mGridCardWidth = gridCardWidth;
    }

    public void updateAudios(ArrayList<AudioWrapter> audios) {
        mAudios = audios;
        notifyDataSetChanged();
    }

    public void addAudio(AudioWrapter audio) {
        int position = getItemCount();

        Log.d(TAG, "addTask position " + position);

        mAudios.add(position, audio);
        notifyItemInserted(position);
    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        Log.d(TAG, "getItemViewType position = " + position + ", type = " + type);
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    @Override
    public int getItemCount() {
        return mAudios.size();
    }

    public void setPlayStatus(int pos,final int resId){
        if (pos > mAudios.size()) {
            return;
        }

        AudioWrapter audioWrapter = mAudios.get(pos);
        if (audioWrapter != null) {
            audioWrapter.holder.mPlayStatus.setImageResource(resId);
        }
    }

    @MainThread
    public void clear() {
        mAudios.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {
        private View mListItem;
        private ImageView mPlayStatus;
        private TextView mDuration;
        private TextView mFileName;
        private TextView mArtlist;

        public ViewHolder(View v) {
            super(v);
            v.setOnFocusChangeListener(this);
            mListItem = v.findViewById(R.id.audio_item_list_view);
            mPlayStatus = (ImageView) v.findViewById(R.id.item_audio_play_status);
            mFileName = (TextView) v.findViewById(R.id.item_audio_list_filename);
            mDuration = (TextView) v.findViewById(R.id.item_audio_list_duration);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
        }
    }
}
