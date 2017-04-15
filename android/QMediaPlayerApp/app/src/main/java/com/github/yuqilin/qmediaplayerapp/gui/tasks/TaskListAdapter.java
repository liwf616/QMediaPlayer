/**
 * Created by liwenfeng on 17/4/11.
 */

package com.github.yuqilin.qmediaplayerapp.gui.tasks;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.yuqilin.qmediaplayerapp.R;
import com.github.yuqilin.qmediaplayerapp.VideoPlayerActivity;
import com.github.yuqilin.qmediaplayerapp.media.MediaTask;
import com.github.yuqilin.qmediaplayerapp.util.ITaskEventHandler;

import java.util.ArrayList;
import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{

    private ITaskEventHandler mTaskEventsHandler;
    private ArrayList<MediaTask> mTasks = new ArrayList<>();

    public final static String TAG = "TaskListAdapter";

    public TaskListAdapter(ITaskEventHandler eventHandler) {
        super();
        mTaskEventsHandler = eventHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder viewType " + viewType);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate( R.layout.item_task_list, parent, false);

        return new TaskListAdapter.ViewHolder(v);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MediaTask media = (MediaTask) view.getTag();
            mTaskEventsHandler.onClick(view, 0, media);
        }
    };

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder position " + position);

        MediaTask media = mTasks.get(position);
        if (media == null) {
            return;
        }

//        holder.mTaskStatusPic.setImageIcon("");
        holder.mFileName.setText(media.getVideoPath().substring(media.getVideoPath().lastIndexOf('/') + 1));
        holder.mProcessText.setText(String.format("%s/%s","00:00:00", VideoPlayerActivity.generateTime(media.getDuration())));
        holder.mListItem.setTag(media);
        holder.mListItem.setOnClickListener(mOnClickListener);

        Log.d(TAG, "position[" + position + "]: " + media.getVideoPath());
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Log.d(TAG, "onBindViewHolder position " + position + ", payloads size " + payloads.size());
            onBindViewHolder(holder, position);
        }
    }

    public void onViewRecycled(TaskListAdapter.ViewHolder holder) {
        Log.d(TAG, "onViewRecycled");
        super.onViewRecycled(holder);
    }

    public void updateVideos(ArrayList<MediaTask> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    public void addVideo(int position, MediaTask task) {
        mTasks.add(position, task);
        notifyItemInserted(position);

        Log.d(TAG, "addVideo position " + position);
    }

    public void addVideo(MediaTask task) {
        int position = getItemCount();

        addVideo(position, task);

        Log.d(TAG, "addVideo position " + position);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @MainThread
    public void clear() {
        mTasks.clear();
    }

//    void setGridCardWidth(int gridCardWidth) {
//        mGridCardWidth = gridCardWidth;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {
        private View mListItem;
        private ImageView mTaskStatusPic;
        private TextView mFileName;
        private TextView mProcessText;

        public ViewHolder(View v) {
            super(v);
            v.setOnFocusChangeListener(this);

            mListItem = v.findViewById(R.id.task_list_item_view);
            mTaskStatusPic = (ImageView) v.findViewById(R.id.item_task_list_status);
            mFileName = (TextView) v.findViewById(R.id.item_task_list_filename);
            mProcessText = (TextView) v.findViewById(R.id.item_video_task_process_text);
        }

        public void onClick(View v) {
            int position = getLayoutPosition();
            mTaskEventsHandler.onClick(v, position, mTasks.get(position));
        }

        public void onMoreClick(View v){
//            mTaskEventsHandler.onCtxClick(v, getLayoutPosition(), null);
        }

        public boolean onLongClick(View v) {
            int position = getLayoutPosition();
            return mTaskEventsHandler.onLongClick(v, position, mTasks.get(position));
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
        }
    }

}
