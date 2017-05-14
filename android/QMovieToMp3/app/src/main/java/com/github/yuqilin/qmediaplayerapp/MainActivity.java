package com.github.yuqilin.qmediaplayerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;

import com.github.yuqilin.qmediaplayerapp.gui.mp3.MyMp3Fragment;
import com.github.yuqilin.qmediaplayerapp.gui.tasks.TaskFragment;
import com.github.yuqilin.qmediaplayerapp.gui.video.VideoFragment;
import com.github.yuqilin.qmediaplayerapp.media.VideoWrapper;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static String[] sPageTitles = {"HOME", "VIDEOS", "FOLDERS"};

//    private EditText mVideoPathEdit;
//    private Button mPlayBtn;

    private ViewPager mViewPager;
    private SmartTabLayout mViewPagerTab;
    private FragmentPagerAdapter mAdpter;

    private List<BaseFragment> mFragments = new ArrayList<>();

    private ArrayList<VideoWrapper> mVideos = new ArrayList<>();

    private VideoFragment mVideoFragment;

    private TaskFragment  mTaskFragment;

    public MyMp3Fragment getmMyMp3Fragment() {
        return mMyMp3Fragment;
    }

    private MyMp3Fragment mMyMp3Fragment;

    private AsyncTask<String, Integer, String> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mFragments.add(new HomeFragment());
        mVideoFragment = new VideoFragment();
        mFragments.add(mVideoFragment);

        mMyMp3Fragment= new MyMp3Fragment(this);
        mFragments.add(mMyMp3Fragment);

        mTaskFragment = new TaskFragment(this);
        mFragments.add(mTaskFragment);

        mVideoFragment.setTaskFragment(mTaskFragment);

        mAdpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragments.get(position).getPageTitle();
            }
        };

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdpter);

        mViewPagerTab = (SmartTabLayout) findViewById(R.id.main_viewpagertab);
        mViewPagerTab.setCustomTabView(R.layout.custom_tab, R.id.custom_text);
        mViewPagerTab.setDividerColors(getResources().getColor(R.color.transparent));
        mViewPagerTab.setViewPager(mViewPager);
    }

    public static void launch(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            //back key Constant Value: 4 (0x00000004)
            //创建退出对话框
            AlertDialog.Builder isExit=new AlertDialog.Builder(this);
            //设置对话框标题
            isExit.setTitle("Message Alert");
            //设置对话框消息
            isExit.setMessage("Do you want to quit ?");
            // 添加选择按钮并注册监听
            isExit.setPositiveButton("YES",diaListener);
            isExit.setNegativeButton("NO",diaListener);
            //对话框显示
            isExit.show();
        }
        return false;
    }

    void quitActivity() {
        if(mMyMp3Fragment != null) {
            mMyMp3Fragment.stopPlaying();
        }

        finish();
    }

    DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int buttonId) {
            // TODO Auto-generated method stub
            switch (buttonId) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    quitActivity();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
                    //什么都不做
                    break;
                default:
                    finish();
                    break;
            }
        }
    };

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
