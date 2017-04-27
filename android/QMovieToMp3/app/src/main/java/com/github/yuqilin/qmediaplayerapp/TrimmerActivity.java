package com.github.yuqilin.qmediaplayerapp;

/**
 * Created by liwenfeng on 17/4/26.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class TrimmerActivity extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

        Intent extraIntent = getIntent();
        String path = "";
        int duration = 100;

        if (extraIntent != null) {
            path = extraIntent.getStringExtra("videoPath");
            duration = extraIntent.getIntExtra("duration", 100);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.trimming_progress));

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(duration);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnK4LVideoListener(this);
            mVideoTrimmer.setVideoURI(Uri.parse(path));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onTrimStarted() {
//        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
//        mProgressDialog.cancel();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(TrimmerActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setDataAndType(uri, "video/mp4");
//        startActivity(intent);
        finish();
    }

    @Override
    public void getTranscodeResult(final Uri uri, int duration, int startPosition,
                                   int endPositon, String bitrate, String type, int vbr) {

        Intent intent = new Intent();
        intent.putExtra("videoPath", uri.getPath());
        intent.putExtra("vbr", vbr);
        intent.putExtra("type", type);
        intent.putExtra("bits", bitrate);
        intent.putExtra("duration", duration);
        intent.putExtra("startTime", startPosition );
        intent.putExtra("endTime", endPositon );

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
