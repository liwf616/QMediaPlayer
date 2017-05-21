package com.github.yuqilin.qmediaplayerapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.yuqilin.qmediaplayerapp.util.ShareUtils;

public class AboutActivity extends BaseActivity {
    private ImageView mBackIv;
    LinearLayout linkll, contactll, ratell;
    TextView versionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mBackIv = (ImageView)findViewById(R.id.back);
        linkll = (LinearLayout)findViewById(R.id.link);
        contactll = (LinearLayout)findViewById(R.id.contact);
        ratell = (LinearLayout)findViewById(R.id.rate);
        versionTv = (TextView)findViewById(R.id.version);

        initState();

        versionTv.setText(String.format(getString(R.string.about_version),getVersion()));

        initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void initListeners() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linkll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        contactll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.adviceEmail(AboutActivity.this);
            }
        });

        ratell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getPackageName();
                launchAppDetail(appPackageName, "com.android.vending");
            }
        });
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected int getContentViewId() {
        return 0;
    }


    @Override
    protected int getFragmentContentId() {
        return 0;
    }
}
