package com.github.yuqilin.qmediaplayerapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;


public class WelcomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startNextPage();
    }

    private void startNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.launch(WelcomeActivity.this);
                finish();
            }
        }, 1000);
    }
}
